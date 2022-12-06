package aoc.y2022.day3

import puzzle

fun main() {
    puzzle { part1(linesFrom("test.txt")) }
    puzzle { part1(linesFrom("input.txt")) }

    puzzle { part2(linesFrom("test.txt")) }
    puzzle { part2(linesFrom("input.txt")) }
}

private fun part1(input: List<String>): Int = input.asSequence()
    .map { it.chunked(it.length / 2) }
    .map { it[0].toSet() intersect it[1].toSet() }
    .flatMap { it }
    .sumOf { prioritize(it) }

private fun part2(input: List<String>): Int = input.asSequence()
    .windowed(3, 3)
    .map { it[0].toSet() intersect it[1].toSet() intersect it[2].toSet() }
    .flatMap { it }
    .sumOf { prioritize(it) }

private fun prioritize(c: Char) = if (c in 'a'..'z') c.code - 96 else c.code - 38
