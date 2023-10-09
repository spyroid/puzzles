package aoc.y2017.day18

import gears.puzzle
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

private fun main() {
//    puzzle { duet(inputLines()) }
    puzzle { duet2(inputLines()) }
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
