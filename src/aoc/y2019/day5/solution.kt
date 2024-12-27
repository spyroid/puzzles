package aoc.y2019.day5

import gears.findInts
import gears.puzzle

fun main() {
    puzzle("1") { sunnyChanceAsteroids(input().findInts(), 1) }
    puzzle("2") { sunnyChanceAsteroids(input().findInts(), 5) }
}

private fun sunnyChanceAsteroids(data: List<Int>, input: Int) = IntComputer.of(data, listOf(input)).run()

data class IntComputer(val program: MutableList<Int>, var input: ArrayDeque<Int>, var output: Int = 0, var ip: Int = 0, var terminated: Boolean = false) {

    companion object {
        fun of(p: List<Int>, i: List<Int>) = IntComputer(p.toMutableList(), ArrayDeque(i))
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

    fun run() = generateSequence(false) { execute() }.first { it }.let { output }

    fun execute(): Boolean {
        if (terminated) return terminated
        val inst = Instruction.of(readAt())
        val (a, b, addr) = Triple(readAt(1, inst.flags), readAt(2, inst.flags), readAt(3))
        when (inst.op) {
            1 -> program.set(addr, a + b).also { ip += 4 }
            2 -> program.set(addr, a * b).also { ip += 4 }
            3 -> program.set(readAt(1), input.removeFirst()).also { ip += 2 }
            4 -> output = a.also { ip += 2 }
            5 -> if (a != 0) ip = b else ip += 3
            6 -> if (a == 0) ip = b else ip += 3
            7 -> program.set(addr, (a < b).compareTo(false)).also { ip += 4 }
            8 -> program.set(addr, (a == b).compareTo(false)).also { ip += 4 }
            else -> terminated = true
        }
        return terminated
    }
}