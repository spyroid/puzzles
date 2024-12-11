package aoc.y2023.day10

import gears.Direction
import gears.Grid
import gears.Point
import gears.puzzle

private fun main() {
    puzzle("1 & 2") { pipeMaze(inputLines()) }
}

private fun pipeMaze(input: List<String>): Any {
    var grid = Grid.of(input) { it }
    var now = grid.all().first { it.v == 'S' }
    val seen = mutableSetOf(now.p)
    val q = ArrayDeque(listOf(now))
    var predict = setOf('|', '-', 'J', 'L', '7', 'F')
    while (q.isNotEmpty()) {
        now = q.removeFirst()
        val around = grid.around4(now.p)
            .filter { it.v != '.' && it.p !in seen }
            .filter { gv ->
                (now.v in "S|JL" && gv.p == now.p - Direction.UP && gv.v in "|7F") ||
                        (now.v in "S|7F" && gv.p == now.p - Direction.DOWN && gv.v in "|JL") ||
                        (now.v in "S-J7" && gv.p == now.p - Direction.RIGHT && gv.v in "-LF") ||
                        (now.v in "S-LF" && gv.p == now.p - Direction.LEFT && gv.v in "-J7")
            }.onEach { gv ->
                if (now.v == 'S') {
                    when (gv.p) {
                        now.p - Direction.UP -> predict = predict.intersect(setOf('|', 'J', 'L'))
                        now.p - Direction.DOWN -> predict = predict.intersect(setOf('|', '7', 'F'))
                        now.p - Direction.RIGHT -> predict = predict.intersect(setOf('-', 'J', '7'))
                        now.p - Direction.LEFT -> predict = predict.intersect(setOf('-', 'L', 'F'))
                    }
                }
            }
        seen.addAll(around.map { it.p }).also { q.addAll(around) }
    }
    grid = grid.clone { x, y, e ->
        when {
            Point(x, y) in seen -> if (e == 'S') predict.first() else e
            else -> '.'
        }
    }
    val others = mutableSetOf<Point>()

    for (y in 0..grid.maxY()) {
        var inside = false
        var up = false
        for (x in 0..grid.maxX()) {
            when (val ch = grid.at(x, y)!!) {
                '|' -> inside = !inside
                in "LF" -> up = ch == 'L'
                in "7J" -> {
                    val c = if (up) 'J' else '7'
                    if (ch != c) inside = !inside
                    up = false
                }
            }
            if (!inside) others.add(Point(x, y))
        }
    }
    others.addAll(seen)
    return seen.size / 2 to (grid.maxX() + 1) * (grid.maxY() + 1) - others.size
}
