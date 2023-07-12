package aoc.y2020.day13

import gears.puzzle

private fun main() {
    puzzle { part1(linesFrom("test.txt")) }
    puzzle { part1(linesFrom("input.txt")) }
    puzzle { part2(linesFrom("test.txt")) }
    puzzle { part2(linesFrom("input.txt")) }
}

private fun part2(input: List<String>): Long {
    val all = input.last().split(",").map { if (it == "x") 1 else it.toLong() }
    var time = 0L
    var step = all.first()

    all.drop(1).forEachIndexed { i, bus ->
        while ((time + i + 1) % bus != 0L) time += step
        step *= bus
    }

    return time
}

private fun part1(input: List<String>): Int {
    val time = input.first().toInt()
    return input.last()
        .split(",")
        .asSequence()
        .filter { it != "x" }
        .map { it.toInt() }
        .map { Pair(it, (time - time % it) + it - time) }
        .sortedBy { it.second }
        .first()
        .let { it.first * it.second }
}

