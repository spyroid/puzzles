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
    for (i in input.indices) for (j in (i + 1)..input.lastIndex) {
        input[i].zip(input[j])
            .filter { it.first == it.second }
            .map { it.first }
            .let { if (it.size + 1 == input[i].length) return it.joinToString("") }
    }
    return 0
}
