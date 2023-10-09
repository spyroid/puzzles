package aoc.y2017.day18

import gears.puzzle
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.time.withTimeout
import java.time.Duration
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

private fun main() {
//    puzzle { duet(inputLines()) }
//    puzzle { duet2(inputLines()) }
    puzzle { Day18Coroutines(inputLines()).solvePart2() }
}

private fun duet2(lines: List<String>): Any = runBlocking {
    val p1 = ConcurrentLinkedQueue<Long>()
    val p0 = ConcurrentLinkedQueue<Long>()
    async { CPU1(p0, p1, true).apply { regs["p"] = 1 }.run(lines) }
    async { CPU1(p1, p0, true).apply { regs["p"] = 0 }.run(lines).sent }.await()
}

private fun duet(lines: List<String>): Any = runBlocking {
    async { CPU1().run(lines).outgoing.last() }.await()
}

private class CPU1(
    val incoming: Queue<Long> = ConcurrentLinkedQueue(),
    val outgoing: Queue<Long> = ConcurrentLinkedQueue(),
    val part2: Boolean = false
) {

    val regs = mutableMapOf<String, Long>()
    private var ip = 0
    var sent = 0

    suspend fun run(instructions: List<String>): CPU1 {
        var counter = 0
        while (ip in instructions.indices) {
            execute(instructions[ip])
            counter++
//            println("${this}")
//            delay(20)
        }
        println(counter)
        return this
    }

    private suspend fun execute(instruction: String) {
        val (inst, rx, op) = instruction.split(" ").plus("x")
        when (inst) {
            "snd" -> outgoing.add(regs.v(rx)).also { sent++ }
            "set" -> regs[rx] = regs.v(op)
            "add" -> regs[rx] = regs.v(rx) + regs.v(op)
            "mul" -> regs[rx] = regs.v(rx) * regs.v(op)
            "mod" -> regs[rx] = regs.v(rx) % regs.v(op)
            "rcv" -> {
                if (part2) {
                    delay(1000)
                    val v = incoming.poll()
                    if (v == null) ip = -2 else regs[rx] = v
                } else {
                    if (regs.v(rx) != 0L) ip = -2
                }
            }

            "jgz" -> if (regs.v(rx) > 0L) ip += regs.v(op).toInt().dec()
        }
        ip += 1
    }
}

private fun MutableMap<String, Long>.v(v: String) = v.toLongOrNull() ?: getOrPut(v) { 0 }
private fun MutableMap<String, Long>.deref(v: String) = v.toLongOrNull() ?: getOrPut(v) { 0 }

class Day18Coroutines(private val input: List<String>) {

    fun solvePart2(): Long = runBlocking {
        val program0Receive = Channel<Long>(Channel.UNLIMITED)
        val program1Receive = Channel<Long>(Channel.UNLIMITED)

        async {
            MachinePart2(
                registers = mutableMapOf("p" to 0L),
                send = program1Receive,
                receive = program0Receive
            ).runUntilStop(input)
        }

        async {
            MachinePart2(
                registers = mutableMapOf("p" to 1L),
                send = program0Receive,
                receive = program1Receive
            ).runUntilStop(input)
        }.await()

    }

    data class MachinePart2(
        private val registers: MutableMap<String, Long> = mutableMapOf(),
        private var pc: Int = 0,
        private var sent: Long = 0,
        private val send: Channel<Long>,
        private val receive: Channel<Long>
    ) {

        suspend fun runUntilStop(instructions: List<String>): Long {
            do {
                instructions.getOrNull(pc)?.let {
                    execute(it)
                }
            } while (pc in instructions.indices)
            return sent
        }

        private suspend fun execute(instruction: String) {
            val parts = instruction.split(" ")
            when (parts[0]) {
                "snd" -> {
                    send.send(registers.deref(parts[1]))
                    sent += 1
                }

                "set" -> registers[parts[1]] = registers.deref(parts[2])
                "add" -> registers[parts[1]] = registers.deref(parts[1]) + registers.deref(parts[2])
                "mul" -> registers[parts[1]] = registers.deref(parts[1]) * registers.deref(parts[2])
                "mod" -> registers[parts[1]] = registers.deref(parts[1]) % registers.deref(parts[2])
                "rcv" ->
                    try {
                        withTimeout(Duration.ofSeconds(1)) {
                            registers[parts[1]] = receive.receive()
                        }
                    } catch (e: Exception) {
                        pc = -2 // Die
                    }

                "jgz" ->
                    if (registers.deref(parts[1]) > 0L) {
                        pc += registers.deref(parts[2]).toInt().dec()
                    }

                else -> throw IllegalArgumentException("No such instruction ${parts[0]}")
            }
            pc += 1
        }
    }
}
