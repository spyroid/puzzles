package aoc.y2019.day5

import gears.findInts
import gears.puzzle

private fun main() {
    puzzle("1") { sunnyChanceAsteroids(input().findInts(), 1) }
    puzzle("2") { sunnyChanceAsteroids(input().findInts(), 5) }
}

private fun sunnyChanceAsteroids(data: List<Int>, input: Int): Any {
    var (program, ip, output) = Triple(data.toTypedArray(), 0, 0)

    fun readAt(offset: Int = 0, flags: List<Boolean> = emptyList()): Int {
        return program[(ip + offset) % program.size].let { p ->
            if (!flags.getOrElse(offset - 1) { true }) readAt(p - ip) else p
        }
    }

    while (true) {
        val inst = Instruction.of(readAt())
        val (a, b, addr) = listOf(readAt(1, inst.flags), readAt(2, inst.flags), readAt(3))
        when (inst.op) {
            1 -> program.set(addr, a + b).also { ip += 4 }
            2 -> program.set(addr, a * b).also { ip += 4 }
            3 -> program.set(readAt(1), input).also { ip += 2 }
            4 -> output = a.also { ip += 2 }
            5 -> if (a != 0) ip = b else ip += 3
            6 -> if (a == 0) ip = b else ip += 3
            7 -> program.set(addr, (a < b).compareTo(false)).also { ip += 4 }
            8 -> program.set(addr, (a == b).compareTo(false)).also { ip += 4 }
            else -> return output
        }
    }
}

private data class Instruction(val op: Int, val flags: List<Boolean>) {
    companion object {
        fun of(s: Int) = s.toString().padStart(5, '0').let { ss ->
            Instruction(ss.drop(3).toInt(), ss.dropLast(2).reversed().map { it == '1' })
        }
    }
}