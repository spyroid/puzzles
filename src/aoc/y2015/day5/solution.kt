package aoc.y2015.day5

import gears.puzzle

private fun main() {
    puzzle { part1(linesFrom("test.txt")) }
    puzzle { part1(linesFrom("input.txt")) }
}

private fun part1(all: List<String>): Int {
    return all
        .filter { line ->
            listOf("ab", "cd", "pq", "xy").none { line.contains(it) }
        }.count { line ->
            val counts = line.groupingBy { it }.eachCount()
            val a = counts.filterKeys { it in "aeiou" }.map { it.value }.sum() > 2
            val b = counts.keys.any { line.contains("$it$it") }
            a && b
        }
}

