package aoc.y2020.day03

import gears.puzzle

private fun main() {
    puzzle { part1(inputLines("test.txt")) }
    puzzle { part1(inputLines("input.txt")) }
    puzzle { part2(inputLines("test.txt")) }
    puzzle { part2(inputLines("input.txt")) }
}

private fun part1(lines: List<String>): Long {
    return countTrees(lines, Pair(3, 1))
}

private fun part2(lines: List<String>): Long {
    return sequenceOf(
        countTrees(lines, Pair(1, 1)),
        countTrees(lines, Pair(3, 1)),
        countTrees(lines, Pair(5, 1)),
        countTrees(lines, Pair(7, 1)),
        countTrees(lines, Pair(1, 2)),
    )
        .reduce { a, v -> v * a }
}

private fun countTrees(lines: List<String>, steps: Pair<Int, Int>): Long {
    var x = 0
    var y = 0
    var count = 0L

    while (y < lines.size) {
        val c = lines[y][x]
        if (c == '#') count += 1
        y += steps.second
        x = (x + steps.first) % lines.first().length
    }
    return count
}
