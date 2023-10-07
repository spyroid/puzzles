package aoc.y2017.day14

import aoc.y2017.day10.knot2
import gears.puzzle

private fun main() {
    puzzle { defragmentator("ljoxqyyw") }
}

private fun defragmentator(input: String): Any {
    var part1 = 0
    for (y in 0..127) {
        val hash = knot2("$input-$y")
            .map { it.digitToInt(16).toString(2).padStart(4, '0') }
            .joinToString("")
        part1 += hash.count { it == '1' }
    }
    return part1
}
