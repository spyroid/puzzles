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

    fun readAt(addr: Int, atPointer: Boolean = true): Int = program.getOrElse(addr) { 0 }.let { if (atPointer) it else readAt(it) }
    fun read(flags: List<Boolean>) = flags.mapIndexed { i, f -> readAt(ip + i + 1, f) }

    while (true) {
        val inst = Instruction.of(readAt(ip))
        val (a, b, addr) = read(inst.flags)
        when (inst.op) {
            1 -> {
                program[addr] = a + b; ip += 2
            }

            2 -> {
                program[addr] = a * b; ip += 2
            }

            3 -> program[program[ip + 1]] = input
            4 -> output = readAt(ip + 1, false)
            else -> break
        }
        ip += 2
    }
    return output
}

private data class Instruction(val op: Int, val flags: List<Boolean>) {
    companion object {
        fun of(s: Int) = s.toString().padStart(5, '0').let { ss ->
            Instruction(ss.drop(3).toInt(), listOf(ss[2], ss[1], '1').map { it == '1' })
        }
    }
}