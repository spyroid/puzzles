package aoc.y2024.day6

import gears.Direction
import gears.Grid
import gears.Point
import gears.puzzle

fun main() {
    puzzle { guardGallivant(inputLines()) }
}

private fun guardGallivant(input: List<String>): Any {
    val grid0 = Grid.of(input) { it }

    fun walk(grid: Grid<Char>): Pair<MutableSet<Pair<Point, Direction>>, Int> {
        var pos = grid.all().first { it.v == '^' }.p.also { grid.set(it, '.') }
        var dir = Direction.DOWN
        var steps = mutableSetOf<Pair<Point, Direction>>(pos to dir)
        while (true) {
            when (grid.at(pos + dir)) {
                '#' -> dir = dir.turnCcw()
                '.' -> pos = pos + dir
                else -> return steps to 0
            }
            if (!steps.add(pos to dir)) return steps to 1
        }
    }

    var p1 = walk(grid0).first.unzip().first.toSet()
    val p2 = p1.drop(1).sumOf { p -> Grid.of(input) { it }.also { it.set(p, '#') }.let { walk(it).second } }
    return p1.size to p2
}
