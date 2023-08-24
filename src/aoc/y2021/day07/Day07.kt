package aoc.y2021.day07

import gears.puzzle
import kotlin.math.abs

fun main() {
    puzzle { part1And2(linesFrom("input.txt").first()) }
    puzzle { part1And2(linesFrom("input.txt").first(), true) }
}

private fun part1And2(line: String, expensiveFuel: Boolean = false): Int {
    val seq = line.split(",").map { it.toInt() }
    return seq
        .sorted()
        .let { (it.first()..it.last()) }
        .map { pos ->
            seq.map { abs(it - pos) }
                .sumOf { v -> if (expensiveFuel) (v * (v + 1)) / 2 else v }
        }
        .minOf { it }
}
