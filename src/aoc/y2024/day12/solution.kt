package aoc.y2024.day12

import gears.Grid
import gears.Point
import gears.puzzle

fun main() {
    puzzle { gardenGroups(inputLines()) }
}

private fun gardenGroups(input: List<String>): Any {
    val grid = Grid.of(input) { it }
    val seen = mutableSetOf<Point>()

    val res = grid.all().sumOf { (p, c) ->
        val connected = mutableSetOf<Point>()
        val q = ArrayDeque<Point>()
        q.add(p)
        var perimeter = 0
        while (q.isNotEmpty()) {
            val cur = q.removeFirst()
            if (!seen.add(cur)) continue
            connected.add(cur)
            perimeter += grid.neighbours(cur).filter { it != c }.size
            q.addAll(grid.around4(cur).filter { it.v == c }.map { it.p })
        }
        perimeter * connected.size
    }

    return res
}

private fun Grid<Char>.neighbours(p: Point): List<Char?> {
    return listOf(
        at(p.x - 1, p.y),
        at(p.x + 1, p.y),
        at(p.x, p.y + 1),
        at(p.x, p.y - 1)
    )
}

