package aoc.y2025.day9

import gears.Point
import gears.combinations
import gears.puzzle
import kotlin.math.absoluteValue

fun main() {
    puzzle {
        movieTheater(inputLines())
    }
}

private fun movieTheater(input: List<String>): Any {
    val points = input.map { Point.of(it) }

    val part1 = points.combinations().maxOf { (a, b) -> (a.x - b.x + 1L).absoluteValue * (a.y - b.y + 1L).absoluteValue }

    return part1
}
