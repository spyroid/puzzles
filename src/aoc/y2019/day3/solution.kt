package aoc.y2019.day3

import gears.Direction
import gears.Point
import gears.puzzle

fun main() {
    puzzle { crossedWires(inputLines()) }
}

private fun crossedWires(input: List<String>): Any {
    fun walk(path: String): Set<Point> = buildSet {
        var p = Point.ZERO
        var steps = 0
        path.split(",").forEach { s ->
            val (dir, length) = Direction.of(s.first()) to s.drop(1).toInt()
            p = (0..<length).fold(p) { pp, _ -> pp.plus(dir).also { it.value = ++steps }.also { add(it) } }
        }
    }

    val a = walk(input.first())
    val b = walk(input.last())
    val c = a.intersect(b)

    val part2 = c.minOf { p -> a.first { it == p }.value + b.first { it == p }.value }
    return c.minOf { it.manhattan() } to part2
}
