package aoc.y2017.day5

import gears.puzzle
import gears.toInts

private fun main() {
    puzzle { maze(inputLines("input.txt").toInts()) }
    puzzle { maze(inputLines("input.txt").toInts(), true) }
}

private fun maze(input: List<Int>, part2: Boolean = false): Int {
    val program = input.toMutableList()
    var steps = 0
    var ip = 0
    while (ip in program.indices) {
        ip += program[ip].also {
            if (part2 && program[ip] >= 3) program[ip] = program[ip] - 1 else program[ip] = program[ip] + 1
        }
        steps++
    }
    return steps
}
