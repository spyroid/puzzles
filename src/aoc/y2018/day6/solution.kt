package aoc.y2018.day6

import gears.Point
import gears.bounds
import gears.puzzle

fun main() {
    puzzle { `Chronal Coordinates`(inputLines()) }
}

private fun `Chronal Coordinates`(input: List<String>): Any {
    val centroids = input.map { Point.of(it, ", ") }.toSet()
    val (p1, p2) = centroids.bounds()

    val unboundedCentroids = mutableSetOf<Point>()
    val part1 = p1.allInBounds(p2).mapNotNull { p ->
        val (a, b) = centroids.map { it.manhattan(p) to it }.sortedBy { it.first }
        if (p.x == p1.x || p.y == p1.y || p.x == p2.x || p.y == p2.y) unboundedCentroids.add(a.second)
        if (a.first != b.first) a.second else null
    }.groupingBy { it }.eachCount().filter { it.key !in unboundedCentroids }.maxOf { it.value }

    val part2 = p1.allInBounds(p2).sumOf { p -> if (centroids.sumOf { it.manhattan(p) } < 10_000) 1 else 0.toInt() }

    return part1 to part2
}