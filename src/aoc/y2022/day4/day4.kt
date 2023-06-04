package aoc.y2022.day4

import gears.isFullyOverlaps
import gears.isOverlaps
import gears.puzzle

fun main() {
    puzzle { part1(linesFrom("test.txt")) }
    puzzle { part1(linesFrom("input.txt")) }

    puzzle { part2(linesFrom("test.txt")) }
    puzzle { part2(linesFrom("input.txt")) }
}

private fun part1(input: List<String>) = asRanges(input).count { it[0] isFullyOverlaps it[1] || it[1] isFullyOverlaps it[0] }

private fun part2(input: List<String>) = asRanges(input).count { it[0] isOverlaps it[1] }

private fun asRanges(list: List<String>) = list.map { l ->
    l.split(",").map { p -> p.split("-").let { IntRange(it[0].toInt(), it[1].toInt()) } }
}

