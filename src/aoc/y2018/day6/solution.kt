package aoc.y2018.day6

import gears.Point
import gears.bounds
import gears.inc
import gears.puzzle

fun main() {
    puzzle { `Chronal Coordinates`(inputLines()) }
}

private fun `Chronal Coordinates`(input: List<String>): Any {
    val centroid = input.map { Point.of(it, ", ") }.toSet()
    val (p1, p2) = centroid.bounds()

    val unboundedCentroids = mutableSetOf<Point>()
    val centroidAreas = mutableMapOf<Point, Int>()

    p1.allInBounds(p2).forEach { p ->
        val (a, b) = centroid.map { it.manhattan(p) to it }.sortedBy { it.first }
        if (a.first != b.first) centroidAreas.inc(a.second)
        if (p.x == p1.x || p.y == p1.y || p.x == p2.x || p.y == p2.y) unboundedCentroids.add(a.second)
    }
    val part1 = centroidAreas.filter { it.key !in unboundedCentroids }.maxOf { it.value }
    val part2 = p1.allInBounds(p2).sumOf { p -> if (centroid.sumOf { it.manhattan(p) } < 10_000) 1 else 0.toInt() }

    return part1 to part2
}
