package aoc.y2018.day2

import gears.puzzle

private fun main() {
    puzzle { inventory(inputLines()) }
    puzzle { inventory2(inputLines()) }
}

private fun inventory(input: List<String>) = input
    .map { s -> s.groupingBy { it }.eachCount() }
    .map { m -> m.any { it.value == 2 } to m.any { it.value == 3 } }
    .let { m -> m.count { it.first } * m.count { it.second } }

private fun inventory2(input: List<String>): Any {
    for (i in input.indices - 1) for (j in (i + 1)..input.lastIndex) {
        val s = input[i].zip(input[j]).map { if (it.first != it.second) 0.toChar() else it.first }
        if (s.count { it.code == 0 } == 1) return s.filter { it.code != 0 }.joinToString("")
    }
    return 0
}
