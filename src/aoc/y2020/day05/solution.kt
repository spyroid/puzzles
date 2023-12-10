package aoc.y2020.day05

import gears.puzzle

private fun main() {
    puzzle { part1(inputLines("test.txt")) }
    puzzle { part1(inputLines("input.txt")) }
    puzzle { part2(inputLines("input.txt")) }
}

private fun part1(lines: List<String>): Int = lines.maxOf { decode(it) }

private fun part2(lines: List<String>): Int {
    return lines.asSequence()
        .map { decode(it) }
        .sorted()
        .windowed(2)
        .first { it[1] - it[0] > 1 }
        .first() + 1
}

private fun decode(pass: String): Int {
    val s = pass.map { if (it == 'F' || it == 'L') '0' else '1' }.joinToString("")
    return s.take(7).toInt(2) * 8 + s.takeLast(3).toInt(2)
}

