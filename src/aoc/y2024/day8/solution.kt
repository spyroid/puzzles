package aoc.y2024.day8

import gears.Grid
import gears.Point
import gears.puzzle

fun main() {
    puzzle { resonantCollinearity(inputLines()) }
}

private fun resonantCollinearity(input: List<String>): Any {
    fun Point.nextInLine(other: Point) = Point(other.x * 2 - this.x, other.y * 2 - this.y)
    val grid = Grid.of(input) { if (it == '#') '.' else it }

    val map = grid.all().filter { !".".contains(it.v) }.groupBy({ it.v }, { it.p })

    val p1 = sequence {
        map.values.forEach { list ->
            list.dropLast(1).forEachIndexed { i, a ->
                list.drop(i + 1).forEach { b -> yield(a.nextInLine(b)); yield(b.nextInLine(a)) }
            }
        }
    }
        .filter { grid.at(it) != null }
        .distinct()
        .count()

    return p1
}
