package aoc.y2022.day4

import gears.fullyOverlaps
import gears.overlaps
import gears.puzzle

fun main() {
    puzzle { part1(inputLines("test.txt")) }
    puzzle { part1(inputLines("input.txt")) }

    puzzle { part2(inputLines("test.txt")) }
    puzzle { part2(inputLines("input.txt")) }
}

private fun part1(input: List<String>) = asRanges(input).count { it[0] fullyOverlaps it[1] || it[1] fullyOverlaps it[0] }

private fun part2(input: List<String>) = asRanges(input).count { it[0] overlaps it[1] }

private fun asRanges(list: List<String>) = list.map { l ->
    l.split(",").map { p -> p.split("-").let { IntRange(it[0].toInt(), it[1].toInt()) } }
}

