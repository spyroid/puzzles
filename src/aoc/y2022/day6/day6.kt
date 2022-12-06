package aoc.y2022.day6

import puzzle

fun main() {
    puzzle { findMarker(readLinesFrom("test.txt").first(), 4) }
    puzzle { findMarker(readLinesFrom("input.txt").first(), 4) }

    puzzle { findMarker(readLinesFrom("test.txt").first(), 14) }
    puzzle { findMarker(readLinesFrom("input.txt").first(), 14) }
}

private fun findMarker(input: String, size: Int): Int {
    return input
        .windowed(size = size, step = 1, partialWindows = false)
        .mapIndexed { i, s -> (s.toSet().count() == size) to (size + i) }
        .first { it.first }.second
}

