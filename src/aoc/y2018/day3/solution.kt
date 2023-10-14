package aoc.y2018.day3

import gears.Point
import gears.puzzle

private fun main() {
    puzzle { slice(inputLines()) }
//    puzzle { inventory2(inputLines()) }
}

private val re = "\\d+".toRegex()

private fun slice(input: List<String>): Any {
    val grid = mutableMapOf<Point, Int>()
    val ids = mutableSetOf<Int>()
    for (s in input) {
        val (id, x, y, w, h) = re.findAll(s).map { it.value }.map { it.toInt() }.toList()
        for (i in 0..<h) for (j in 0..<w) {
            grid.compute(Point(x + j, y + i)) { p, v ->
                v?.plus(1) ?: 1
            }
        }
    }
    return grid.filterValues { it > 1 }.count()
}
