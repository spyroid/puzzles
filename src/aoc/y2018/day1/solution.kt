package aoc.y2018.day1

import gears.puzzle

private fun main() {
    puzzle { inputLines().sumOf { it.toInt() } }
    puzzle { calibration(inputLines().map { it.toInt() }) }
}

private fun calibration(input: List<Int>): Any {
    val seen = mutableSetOf<Int>()
    return generateSequence(0 to 0) { p -> Pair(p.first + 1, p.second + input[p.first % input.size]) }
        .first { !seen.add(it.second) }
        .second
}
