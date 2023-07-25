package aoc.y2015.day6

import gears.Point
import gears.puzzle

private fun main() {
    puzzle { part1(linesFrom("test.txt")) }
    puzzle { part1(linesFrom("input.txt")) }
    puzzle { part2(linesFrom("input.txt")) }
}

private fun part1(all: List<String>): Int {
    val set = mutableSetOf<Point>()
    all.forEach {
        val (op, xRange, yRange) = parse(it)
        when (op) {
            "turn on" -> xRange.forEach { x -> yRange.forEach { y -> set.add(Point(x, y)) } }
            "turn off" -> xRange.forEach { x -> yRange.forEach { y -> set.remove(Point(x, y)) } }
            else -> xRange.forEach { x ->
                yRange.forEach { y ->
                    val p = Point(x, y)
                    if (set.contains(p)) set.remove(p) else set.add(p)
                }
            }
        }
    }
    return set.size
}

private fun part2(all: List<String>): Int {
    val map = mutableMapOf<Point, Int>()
    all.forEach {
        val (op, xRange, yRange) = parse(it)
        val i = when (op) {
            "turn on" -> 1
            "turn off" -> -1
            else -> 2
        }
        xRange.forEach { x ->
            yRange.forEach { y ->
                map.getOrPut(Point(x, y)) { 0 }.also { v -> map[Point(x, y)] = maxOf(v + i, 0) }
            }
        }
    }
    return map.values.sum()
}

val r = "(.+)\\s(\\d+),(\\d+)\\s.*\\s(\\d+),(\\d+)".toRegex()
private fun parse(s: String): Triple<String, IntRange, IntRange> {
    val (op, x1, y1, x2, y2) = r.find(s)?.groupValues?.drop(1) ?: listOf()
    return Triple(op, IntRange(x1.toInt(), x2.toInt()), IntRange(y1.toInt(), y2.toInt()))
}
