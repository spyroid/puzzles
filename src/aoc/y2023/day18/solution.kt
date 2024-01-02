package aoc.y2023.day18

import gears.puzzle
import kotlin.math.abs

private fun main() {
    puzzle("1") { lavaductLagoon(inputLines()) }
    puzzle("2") { lavaductLagoon(inputLines(), true) }
}

private fun lavaductLagoon(input: List<String>, part2: Boolean = false): Any {
    val points = mutableListOf(Point(0, 0))
    var x = 0L
    input.onEach { s ->
        val (a, b, c) = s.split(" ")
        val (dir, len) = when {
            part2 -> c.drop(2).dropLast(1).let { Direction.of(it.last()) to it.dropLast(1).toLong(16) }
            else -> Direction.of(a.first()) to b.toLong()
        }
        val pp = points.last() + Point(dir.x, dir.y) * len
        points.add(pp).also { x += len }
    }
    return points.shoelaceArea().toLong() - x / 2 + 1 + x
}

private fun List<Point>.shoelaceArea() = zipWithNext { a, b -> a.x * b.y - b.x * a.y }.sum()
    .let { abs(it + last().x * first().y - first().x * last().y) / 2.0 }

private data class Point(var x: Long, var y: Long) {
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    operator fun times(factor: Long) = Point(x * factor, y * factor)
}

private enum class Direction(var x: Long, var y: Long) {
    RIGHT(1, 0),
    DOWN(0, 1),
    LEFT(-1, 0),
    UP(0, -1),
    NOTHING(0, 0);

    companion object {
        fun of(ch: Char) =
            when (ch) {
                'R', '0' -> RIGHT
                'D', '1' -> DOWN
                'L', '2' -> LEFT
                'U', '3' -> UP
                else -> NOTHING
            }
    }
}