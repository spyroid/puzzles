package aoc.y2022.day6

import gears.puzzle

fun main() {
    puzzle("test 1") { findMarker(inputLines("test.txt").first(), 4) }
    puzzle { findMarker(inputLines("input.txt").first(), 4) }

    puzzle("test 2") { findMarker(inputLines("test.txt").first(), 14) }
    puzzle { findMarker(inputLines("input.txt").first(), 14) }
}

private fun findMarker(input: String, size: Int): Int = input.windowed(size).indexOfFirst { it.toSet().count() == size } + size
