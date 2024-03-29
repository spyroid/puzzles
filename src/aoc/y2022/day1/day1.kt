package aoc.y2022.day1

import gears.puzzle

fun main() {
    puzzle { part1(inputLines("day1.test.txt"), 1) }
    puzzle { part1(inputLines("day1.input.txt"), 1) }
    puzzle { part1(inputLines("day1.input.txt"), 3) }
}

private fun part1(input: List<String>, count: Int): Int {
    return input.fold(mutableListOf(0)) { acc, s ->
        if (s.isNotBlank()) acc[acc.lastIndex] = s.toInt() + acc.last() else acc.add(0)
        acc
    }
        .sortedDescending()
        .take(count)
        .sum()
}
