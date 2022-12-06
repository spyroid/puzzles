package aoc.y2022.day6

import puzzle

fun main() {
    puzzle("test 1") { findMarker(linesFrom("test.txt").first(), 4) }
    puzzle { findMarker(linesFrom("input.txt").first(), 4) }

    puzzle("test 2") { findMarker(linesFrom("test.txt").first(), 14) }
    puzzle { findMarker(linesFrom("input.txt").first(), 14) }
}

private fun findMarker(input: String, size: Int): Int = input.windowed(size).indexOfFirst { it.toSet().count() == size } + size
