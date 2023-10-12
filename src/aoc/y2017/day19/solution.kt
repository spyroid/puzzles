package aoc.y2017.day19

import gears.Direction
import gears.Grid
import gears.Point
import gears.puzzle

private fun main() {
    puzzle { tubes(inputLines()) }
}

private fun tubes(lines: List<String>): Any {
    val max = lines.maxOf { it.length }
    val grid = Grid.of(lines.map { it.padEnd(max) }) { it }
    var p = Point(lines.first().indexOf('|'), 0)
    var dir = Direction.UP
    fun walk() = buildString {
        while (true) {
            val c = grid.at(p)
            if (c != null) {
                if (c == '+') {
                    grid[p] = ' '
                    append('+')
                    break
                }
            } else break
            if (c in 'A'..'Z') append(c) else append('.')
            grid[p] = ' '
            p += dir
        }
    }

    val text = mutableListOf<String>()
    while (true) {
        text.add(walk().dropLastWhile {it == '.' })
        if (grid.at(p) == null) break
        for (i in 1..4) {
            if (grid.at(p + dir) == null || grid.at(p + dir) == ' ') dir = dir.turnCcw() else break
        }
        p += dir
    }
    return text.joinToString("").filter { it in 'A'..'Z' } to text.sumOf { it.length }
}
