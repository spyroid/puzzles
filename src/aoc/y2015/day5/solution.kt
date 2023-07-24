package aoc.y2015.day5

import gears.puzzle

private fun main() {
    puzzle { part1(linesFrom("test.txt")) }
    puzzle { part1(linesFrom("input.txt")) }
}

private fun part1(all: List<String>): Int {
    return all
        .filter {
            !it.contains("ab") && !it.contains("cd")
                    && !it.contains("pq") && !it.contains("xy")
        }.count { line ->
            val counts = line.groupingBy { it }.eachCount()
            val a = counts.filterKeys { it in "aeiou" }.map { it.value }.sum() > 2
            val b = counts.keys.any { line.contains("$it$it") }
            a && b
        }
}

