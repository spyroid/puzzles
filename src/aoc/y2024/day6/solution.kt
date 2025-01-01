package aoc.y2024.day6

import gears.Direction
import gears.Grid2
import gears.Point
import gears.puzzle

fun main() {
    puzzle { guardGallivant(inputLines()) }
}

private fun guardGallivant(input: List<String>): Any {
    fun walk(grid: Grid2<Char>): Pair<MutableSet<Pair<Point, Direction>>, Int> {
        var pos = grid.all().first { it.v == '^' }.p
        var dir = Direction.DOWN
        val steps = mutableSetOf<Pair<Point, Direction>>()
        while (steps.add(pos to dir)) {
            when (grid.at(pos + dir)) {
                '#' -> dir = dir.turnCcw()
                '.', '^' -> pos += dir
                else -> return steps to 0
            }
        }
        return steps to 1
    }

    val p1 = walk(Grid2.of(input) { it }).first.unzip().first.toSet()
    val p2 = p1.drop(1).sumOf { p -> Grid2.of(input) { it }.also { it[p] = '#' }.let { walk(it).second } }
    return p1.size to p2
}
