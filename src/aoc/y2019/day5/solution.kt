package aoc.y2019.day5

import gears.findInts
import gears.puzzle

private fun main() {
    puzzle("1 & 2") { sunnyChanceAsteroids(input().findInts()) }
}

private fun sunnyChanceAsteroids(data: List<Int>): Any {
    var (program, ip) = data.toTypedArray() to 0
    val input = 1
    var output = 0

    fun read(addr: Int) = program.getOrNull(addr) ?: -999
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
                ip += 2
            }

            2 -> {
                writeAt(addr, a * b)
                ip += 2
            }

            3 -> writeAt(readAt(1), input)
            4 -> output = readAt(1, inst.flags)
            5 -> if (readAt(1, inst.flags) != 0) ip = readAt(2, inst.flags)
            6 -> if (readAt(1, inst.flags) == 0) ip = readAt(2, inst.flags)
            else -> break
        }
        ip += 2
    }
    return output
}

private data class Instruction(val op: Int, val flags: List<Boolean>) {
    companion object {
        fun of(s: Int) = s.toString().padStart(5, '0').let { ss ->
            Instruction(ss.drop(3).toInt(), listOf(ss[2], ss[1], ss[0]).map { it == '1' })
        }
    }
}