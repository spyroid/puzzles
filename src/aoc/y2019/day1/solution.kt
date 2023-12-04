package aoc.y2019.day1

import gears.puzzle

private fun main() {
    puzzle { inputLines().sumOf { fuel(it.toInt()) } }
    puzzle { inputLines().sumOf { fuel(it.toInt(), false) } }
}

private fun fuel(v: Int, oneStep: Boolean = true) = generateSequence(v) { it / 3 - 2 }
    .drop(1)
    .withIndex()
    .takeWhile { if (oneStep) it.index < 1 else it.value >= 0 }
    .map { it.value }
    .sum()
