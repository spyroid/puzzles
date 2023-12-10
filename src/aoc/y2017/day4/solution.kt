package aoc.y2017.day4

import gears.puzzle

private fun main() {
    puzzle { phrase(inputLines("input.txt")) }
    puzzle { phrase(inputLines("input.txt"), true) }
}

private fun phrase(input: List<String>, part2: Boolean = false) = input.map { line ->
    line.split(" ")
        .map { if (part2) it.toList().sorted().joinToString("") else it }
        .groupingBy { it }
        .eachCount()
        .count { it.value > 1 } == 0
}.count { it }

