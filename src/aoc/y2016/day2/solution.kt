package aoc.y2016.day2

import gears.Direction
import gears.Grid
import gears.Point
import gears.puzzle

private fun main() {
    puzzle { bathroom(linesFrom("input.txt")) }
    puzzle { bathroom(linesFrom("input.txt"), true) }
}

private fun bathroom(lines: List<String>, fancy: Boolean = false): String {
    Direction.setYDown()
    val grid = if (fancy) {
        Grid.of(listOf("--1--", "-234-", "56789", "-ABC-", "--D--")) { it }
    } else {
        Grid.of(listOf("123", "456", "789")) { it }
    }
    return buildString {
        var p = if (fancy) Point(0, 2) else Point(1, 1)
        for (line in lines) {
            for (c in line) {
                val x = p + Direction.of(c)
                if (grid.at(x) != null && grid.at(x) != '-') p = x
            }
            append(grid.at(p))
        }
    }
}
