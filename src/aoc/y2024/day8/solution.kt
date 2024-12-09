package aoc.y2024.day8

import gears.Grid
import gears.Point
import gears.puzzle

fun main() {
    puzzle { resonantCollinearity(inputLines()) }
}

private fun resonantCollinearity(input: List<String>): Any {
    val grid = Grid.of(input) { it }
    fun Point.nextInLine(other: Point, limit: Int) = generateSequence(other to Point(other.x - x, other.y - y)) { (a, b) -> a + b to b }
        .withIndex()
        .takeWhile { (i, v) -> i <= limit && grid.at(v.first) != null }
        .drop(1)
        .map { it.value.first }

    val map = grid.all().filter { it.v != '.' }.groupBy({ it.v }, { it.p })

    fun antidotes(limit: Int = Int.MAX_VALUE) = sequence {
        map.values.forEach { list ->
            list.dropLast(1).forEachIndexed { i, a ->
                list.drop(i + 1).forEach { b -> yieldAll(a.nextInLine(b, limit) + b.nextInLine(a, limit)) }
            }
        }
    }
    return antidotes(1).distinct().count() to antidotes().plus(map.values.flatten()).distinct().count()
}
