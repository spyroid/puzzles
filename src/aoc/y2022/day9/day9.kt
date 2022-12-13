package aoc.y2022.day9

import gears.Direction
import gears.Point
import gears.puzzle

fun main() {
    puzzle("t1") { rope(linesFrom("test.txt"), 2) }
    puzzle("1") { rope(linesFrom("input.txt"), 2) }
    puzzle("2") { rope(linesFrom("input.txt"), 10) }
}

private fun rope(input: List<String>, knots: Int): Int = buildSet {
    val points = MutableList(knots) { Point.zero }
    input.forEach { s ->
        val (dir, count) = s.split(" ").let { Pair(Direction.of(it[0]), it[1].toInt()) }
        repeat(count) {
            points[0] = points[0] + dir
            for (i in 1 until points.size) {
                val dist = points[i - 1] - points[i]
                if (dist.chebyshev() > 1) points[i] = points[i] + dist.asDirection()
            }
            add(points.last())
        }
    }
}.size
