package aoc.y2024.day12

import gears.Direction
import gears.Grid2
import gears.Point
import gears.puzzle

fun main() {
    puzzle { gardenGroups(inputLines()) }
}

private fun gardenGroups(input: List<String>): Any {
    val grid = Grid2.of(input) { it }
    val seen = mutableSetOf<Point>()

    val res = grid.all().map { (p, c) ->
        val connected = mutableSetOf<Point>()
        val q = ArrayDeque<Point>()
        q.add(p)
        var p1 = 0
        var p2 = 0
        while (q.isNotEmpty()) {
            val cur = q.removeFirst()
            if (!seen.add(cur)) continue
            connected.add(cur)
            p1 += grid.neighbours(cur).filter { it != c }.size
            p2 += grid.corners(cur)
            q.addAll(grid.around4(cur).filter { it.v == c }.map { it.p })
        }
        p1 * connected.size to p2 * connected.size
    }

    return res.unzip().let { (a, b) -> a.sum() to b.sum() }
}

private fun Grid2<Char>.neighbours(p: Point): List<Char?> {
    return listOf(
        at(p.x - 1, p.y),
        at(p.x + 1, p.y),
        at(p.x, p.y + 1),
        at(p.x, p.y - 1)
    )
}

private fun Grid2<Char>.corners(p: Point) = (Direction.entries.take(4) + Direction.entries.first()).zipWithNext()
    .filter { (d1, d2) ->
        val a = at(p + d1)
        val b = at(p + d2)
        val c = at(p)
        (a != c && b != c) || (a == c && b == c && at(p + d1 + d2) != c)
    }.size

