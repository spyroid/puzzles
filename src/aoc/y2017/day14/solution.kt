package aoc.y2017.day14

import aoc.y2017.day10.knot2
import gears.Point
import gears.puzzle

private fun main() {
    puzzle { defragmentator("ljoxqyyw") }
}

private fun defragmentator(input: String): Any {
    val all = mutableSetOf<Point>()
    for (y in 0..127) {
        knot2("$input-$y")
            .map { it.digitToInt(16).toString(2).padStart(4, '0') }
            .joinToString("")
            .forEachIndexed { x, c -> if (c == '1') all.add(Point(x, y)) }
    }
    return all.size
}
