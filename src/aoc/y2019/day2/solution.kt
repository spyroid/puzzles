package aoc.y2019.day2

import gears.puzzle
import gears.toInts

private fun main() {
    puzzle { alarm(input().split(",").toInts()) }
    puzzle { alarm2(input().split(",").toInts()) }
}

private fun alarm2(input: List<Int>): Int {
    for (a in 0..99) for (b in 0..99) {
        if (alarm(input, a, b) == 19690720) return a * 100 + b
    }
    return 0
}

private fun alarm(input: List<Int>, a: Int = 12, b: Int = 2): Any {
    val program = input.toMutableList().also { it[1] = a; it[2] = b }
    var ip = 0
    while (ip in program.indices) {
        val (cmd, src1, src2, dest) = program.subList(ip, ip + 4)
        when (cmd) {
            1 -> program[dest] = program[src1] + program[src2]
            2 -> program[dest] = program[src1] * program[src2]
            99 -> break
        }
        ip += 4
    }
    return program[0]
}
