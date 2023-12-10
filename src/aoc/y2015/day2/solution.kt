package aoc.y2015.day2

import gears.puzzle

private fun main() {
    puzzle { part1(inputLines("test.txt")) }
    puzzle { part1(inputLines("input.txt")) }
    puzzle { part2(inputLines("test.txt")) }
    puzzle { part2(inputLines("input.txt")) }
}

private fun part1(all: List<String>) = all.sumOf { line ->
    line.split('x')
        .map { it.toInt() }
        .let { (l, w, h) -> 2 * l * w + 2 * w * h + 2 * h * l + minOf(l * w, w * h, h * l) }
}

private fun part2(all: List<String>) = all.sumOf { line ->
    line.split('x')
        .map { it.toInt() }
        .sorted()
        .let { (a, b, c) -> (2 * a + 2 * b) + (a * b * c) }
}
