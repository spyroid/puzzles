package aoc.y2025.day9

import gears.Point
import gears.circularWindows
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
    fun area(a: Point, b: Point) = ((a.x - b.x).absoluteValue + 1L) * ((a.y - b.y).absoluteValue + 1L)

    val part1 = points.combinations().maxOf { (a, b) -> area(a, b) }

    val lines = points.circularWindows(2).map { (a, b) -> a to b }
    val part2 = points.combinations()
        .map { (a, b) -> Triple(a, b, area(a, b)) }
        .sortedByDescending { it.third }
        .first { (a, b, _) ->
            lines.all { (start, end) ->
                val leftOfRect = maxOf(a.x, b.x) <= minOf(start.x, end.x)
                val rightOfRect = minOf(a.x, b.x) >= maxOf(start.x, end.x)
                val above = maxOf(a.y, b.y) <= minOf(start.y, end.y)
                val below = minOf(a.y, b.y) >= maxOf(start.y, end.y)
                leftOfRect || rightOfRect || above || below
            }
        }.third

    return part1 to part2
}
