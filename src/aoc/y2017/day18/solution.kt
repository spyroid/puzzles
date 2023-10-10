package aoc.y2017.day18

import gears.puzzle
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

private fun main() {
    puzzle { duet(inputLines()) }
    puzzle { duet2(inputLines()) }
}

private fun duet2(lines: List<String>): Any {
    val p1 = ArrayBlockingQueue<Long>(1000)
    val p0 = ArrayBlockingQueue<Long>(1000)
    CPU(p0, p1, true).apply { regs["p"] = 0 }.run(lines)
    return CPU(p1, p0, true).apply { regs["p"] = 1 }.run(lines).get().sent
}

private fun duet(lines: List<String>) = CPU().run(lines).get().outgoing.last()

private class CPU(
    val incoming: BlockingQueue<Long> = ArrayBlockingQueue(1000),
    val outgoing: BlockingQueue<Long> = ArrayBlockingQueue(1000),
    val part2: Boolean = false
) {

    val regs = mutableMapOf<String, Long>()
    private var ip = 0
    var sent = 0

    fun run(instructions: List<String>): CompletableFuture<CPU> = CompletableFuture.supplyAsync {
        while (ip in instructions.indices) execute(instructions[ip])
        this
    }

    private fun execute(instruction: String) {
        val (inst, rx, op) = instruction.split(" ").plus("x")
        when (inst) {
            "snd" -> outgoing.add(regs.v(rx)).also { sent++ }
            "set" -> regs[rx] = regs.v(op)
            "add" -> regs[rx] = regs.v(rx) + regs.v(op)
            "mul" -> regs[rx] = regs.v(rx) * regs.v(op)
            "mod" -> regs[rx] = regs.v(rx) % regs.v(op)
            "rcv" -> if (part2) try {
                regs[rx] = incoming.poll(1, TimeUnit.MILLISECONDS) ?: throw Exception()
            } catch (e: Exception) {
                ip = -2
            } else if (regs.v(rx) != 0L) ip = -2

            "jgz" -> if (regs.v(rx) > 0L) ip += regs.v(op).toInt().dec()
        }
        ip += 1
    }
}

private fun MutableMap<String, Long>.v(v: String) = v.toLongOrNull() ?: getOrPut(v) { 0 }
