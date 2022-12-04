package aoc.y2022.day4

import puzzle

fun main() {
    puzzle { part1(readLinesFrom("test.txt")) }
    puzzle { part1(readLinesFrom("input.txt")) }

    puzzle { part2(readLinesFrom("test.txt")) }
    puzzle { part2(readLinesFrom("input.txt")) }
}

private fun asRanges(list: List<String>) = list.map { line ->
    line.split(",").map { pair -> pair.split("-").let { IntRange(it[0].toInt(), it[1].toInt()) } }
}

private fun part1(input: List<String>) = asRanges(input).count { it[0] isFullyOverlapped it[1] }

private fun part2(input: List<String>) = asRanges(input).count { it[0] isPartiallyOverlapped it[1] }

infix fun IntRange.isFullyOverlapped(other: IntRange): Boolean = (first in other && last in other) || (other.first in this && other.last in this)

infix fun IntRange.isPartiallyOverlapped(other: IntRange): Boolean = (first in other || last in other) || (other.first in this || other.last in this)
