package aoc.y2022.day3

import puzzle

fun main() {
    puzzle { part1(readLinesFrom("test.txt")) }
    puzzle { part1(readLinesFrom("input.txt")) }

    puzzle { part2(readLinesFrom("test.txt")) }
    puzzle { part2(readLinesFrom("input.txt")) }
}

private fun part1(input: List<String>): Int = input.asSequence()
    .map { Pair(it.substring(0 until it.length / 2), it.substring(it.length / 2 until it.length)) }
    .map { it.first.toSet().intersect(it.second.toSet()) }
    .flatMap { it }
    .map { decode(it) }
    .sum()

private fun part2(input: List<String>): Int = input.asSequence()
    .windowed(3, 3)
    .map { it[0].toSet().intersect(it[1].toSet().intersect(it[2].toSet())) }
    .flatMap { it }
    .map { decode(it) }
    .sum()

private fun decode(c: Char) = if (c in 'a'..'z') c.code - 96 else c.code - 38
