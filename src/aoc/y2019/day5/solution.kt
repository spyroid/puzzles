package aoc.y2019.day5

import gears.findInts
import gears.puzzle

private fun main() {
    puzzle("1") { sunnyChanceAsteroids(input().findInts(), 1) }
    puzzle("2") { sunnyChanceAsteroids(input().findInts(), 5) }
}

private fun sunnyChanceAsteroids(data: List<Int>, input: Int): Any {
    var (program, ip) = data.toTypedArray() to 0
    var output = 0

    fun read(addr: Int) = program.getOrElse(addr) { -99999 }
    fun readAt(offset: Int = 0, flags: List<Boolean> = emptyList()): Int {
        val p = read(ip + offset)
        return if (flags.isNotEmpty() && !flags[offset - 1]) read(p) else p
    }

    fun writeAt(addr: Int, v: Int) {
        program[addr] = v
    }

    while (true) {
        val inst = Instruction.of(readAt())
        val (a, b, addr) = listOf(readAt(1, inst.flags), readAt(2, inst.flags), readAt(3))
        when (inst.op) {
            1 -> {
                writeAt(addr, a + b)
                ip += 4
            }

            2 -> {
                writeAt(addr, a * b)
                ip += 4
            }

            3 -> {
                writeAt(readAt(1), input)
                ip += 2
            }

            4 -> {
                output = a
                ip += 2
            }

            5 -> if (a != 0) ip = b else ip += 3
            6 -> if (a == 0) ip = b else ip += 3
            7 -> {
                writeAt(addr, (a < b).compareTo(false))
                ip += 4
            }

            8 -> {
                writeAt(addr, (a == b).compareTo(false))
                ip += 4
            }

            else -> break
        }
    }
    return output
}

private data class Instruction(val op: Int, val flags: List<Boolean>) {
    companion object {
        fun of(s: Int) = s.toString().padStart(5, '0').let { ss ->
            Instruction(ss.drop(3).toInt(), ss.dropLast(2).reversed().map { it == '1' })
        }
    }
}