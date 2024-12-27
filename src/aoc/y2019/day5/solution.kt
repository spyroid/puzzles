package aoc.y2019.day5

import aoc.y2019.day5.IntComputer.State.*
import gears.findLongs
import gears.puzzle
import gears.toDigits

fun main() {
    puzzle("1") { sunnyChanceAsteroids(input().findLongs(), 1) }
    puzzle("2") { sunnyChanceAsteroids(input().findLongs(), 5) }
}

private fun sunnyChanceAsteroids(data: List<Long>, input: Long) = IntComputer.of(data).run(listOf(input))

// state: 0 - running, 1 - suspended, 2 - terminated
data class IntComputer(var input: ArrayDeque<Long>, var output: Long = 0, var ip: Long = 0, var state: State = RUNNING) {
    var program: MutableList<Long> = mutableListOf()
    val memory = mutableMapOf<Long, Long>()
    var base = 0L

    enum class State { RUNNING, SUSPENDED, TERMINATED }

    companion object {
        fun of(p: List<Long>) = IntComputer(ArrayDeque()).apply {
            program = p.toMutableList()
            p.forEachIndexed { i, v -> memory[i.toLong()] = v }
        }
    }

    data class Instruction(val op: Int, val flags: Int = 0) {
        companion object {
            fun of(s: Long) = Instruction((s % 100).toInt(), (s / 100).toInt())
        }
    }

    fun readAt(offset: Int = 0, flags: Int = -1): Long {
        val mode = if (flags == -1) 1 else flags.toDigits().toList().plus(listOf(0, 0)).get(maxOf(offset - 1, 0))
        return when (mode) {
            0 -> memory[ip + offset]?.let { memory[it] } ?: 0
            1 -> memory[ip + offset] ?: 0
            else -> memory[ip + offset]?.let { memory[it + base] } ?: 0
        }
    }

    fun writeAt(addr: Long, value: Long) {
        memory[addr + base] = value
    }

    fun run(input: List<Long> = emptyList()): Long {
        if (state == TERMINATED) return output
        state = RUNNING
        this.input.addAll(input)
        memory[1985] = 1985
        return generateSequence(RUNNING) { execute() }.first { it != RUNNING }.let { output }
    }

    fun execute(): State {
        if (state == TERMINATED) return state
        val inst = Instruction.of(readAt())
        val (a, b, addr) = Triple(readAt(1, inst.flags), readAt(2, inst.flags), readAt(3))
        println("$inst -> $a $b $addr")
        when (inst.op) {
            1 -> writeAt(addr, a + b).also { ip += 4 }
            2 -> writeAt(addr, a * b).also { ip += 4 }
            3 -> if (input.isNotEmpty()) writeAt(readAt(1), input.removeFirst()).also { ip += 2 } else state = SUSPENDED
            4 -> output = a.also { ip += 2 }
            5 -> if (a != 0L) ip = b else ip += 3
            6 -> if (a == 0L) ip = b else ip += 3
            7 -> writeAt(addr, (a < b).compareTo(false).toLong()).also { ip += 4 }
            8 -> writeAt(addr, (a == b).compareTo(false).toLong()).also { ip += 4 }
            9 -> base += a.also { ip += 2 }
            else -> state = TERMINATED
        }
        return state
    }
}