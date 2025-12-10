package aoc.y2025.day9

import aoc.y2025.day9.Rectangle
import gears.Point
import gears.circularWindows
import gears.combinations
import gears.length
import gears.overlaps
import gears.puzzle
import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.math.absoluteValue

fun main() {
    puzzle {
        movieTheater(inputLines())
    }
}

private fun movieTheater(input: List<String>): Any {
    val points = input.map { Point.of(it) }
    val lines: List<Rectangle> = (points + points.first()).zipWithNext().map { (a, b) -> Rectangle.of(a, b) }
    val rectangles = points.combinations().map { (a, b) -> Rectangle.of(a, b) }.sortedByDescending { it.area }

    val part1 = rectangles.first().area
    val part2 = rectangles.first { rectangle -> lines.none { line -> line.overlaps(rectangle.inner) } }.area

    return part1 to part2
}

private data class Rectangle(val x: IntRange, val y: IntRange) {
    lateinit var inner: Rectangle
    val area = x.length().toLong() * y.length()

    companion object {
        fun of(a: Point, b: Point) = Rectangle(min(a.x, b.x)..max(a.x, b.x), min(a.y, b.y)..max(a.y, b.y)).apply {
            inner = Rectangle(x.first + 1..<x.last, y.first + 1..<y.last)
        }
    }

    fun overlaps(other: Rectangle) = x.overlaps(other.x) && y.overlaps(other.y)
}