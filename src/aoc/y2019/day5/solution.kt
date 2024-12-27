package aoc.y2019.day5

import aoc.y2019.day5.IntComputer.State.*
import gears.findInts
import gears.puzzle

fun main() {
    puzzle("1") { sunnyChanceAsteroids(input().findInts(), 1) }
    puzzle("2") { sunnyChanceAsteroids(input().findInts(), 5) }
}

private fun sunnyChanceAsteroids(data: List<Int>, input: Int) = IntComputer.of(data).run(listOf(input))

// state: 0 - running, 1 - suspended, 2 - terminated
data class IntComputer(var input: ArrayDeque<Int>, var output: Int = 0, var ip: Int = 0, var state: State = RUNNING) {
    var program: MutableList<Int> = mutableListOf()

    enum class State { RUNNING, SUSPENDED, TERMINATED }

    companion object {
        fun of(p: List<Int>) = IntComputer(ArrayDeque()).apply { program = p.toMutableList() }
    }

    data class Instruction(val op: Int, val flags: List<Boolean>) {
        companion object {
            fun of(s: Int) = Instruction(s % 100, listOf(((s / 100) % 10) != 0, (s / 1000) != 0))
        }
    }

    fun readAt(offset: Int = 0, flags: List<Boolean> = emptyList()): Int {
        return program[(ip + offset) % program.size].let { p ->
            if (flags.isEmpty() || flags[offset - 1]) p else readAt(p - ip)
        }
    }

    fun run(input: List<Int>): Int {
        if (state == TERMINATED) return output
        state = RUNNING
        this.input.addAll(input)
        return generateSequence(RUNNING) { execute() }.first { it != RUNNING }.let { output }
    }

    fun execute(): State {
        if (state == TERMINATED) return state
        val inst = Instruction.of(readAt())
        val (a, b, addr) = Triple(readAt(1, inst.flags), readAt(2, inst.flags), readAt(3))
        when (inst.op) {
            1 -> program.set(addr, a + b).also { ip += 4 }
            2 -> program.set(addr, a * b).also { ip += 4 }
            3 -> if (input.isNotEmpty()) program.set(readAt(1), input.removeFirst()).also { ip += 2 } else state = SUSPENDED
            4 -> output = a.also { ip += 2 }
            5 -> if (a != 0) ip = b else ip += 3
            6 -> if (a == 0) ip = b else ip += 3
            7 -> program.set(addr, (a < b).compareTo(false)).also { ip += 4 }
            8 -> program.set(addr, (a == b).compareTo(false)).also { ip += 4 }
            else -> state = TERMINATED
        }
        return state
    }
}