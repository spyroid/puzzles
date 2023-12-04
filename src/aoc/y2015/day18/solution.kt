package aoc.y2015.day18

import gears.Grid
import gears.puzzle

private fun main() {
    puzzle { gif(linesFrom("input.txt")) }
    puzzle { gif(linesFrom("input.txt"), true) }
}

private fun gif(lines: List<String>, isStuck: Boolean = false): Int {
    var grid = Grid.of(lines) { it }
    if (isStuck) {
        grid[0, 0] = '#'
        grid[grid.maxX(), 0] = '#'
        grid[grid.maxX(), grid.maxY()] = '#'
        grid[0, grid.maxY()] = '#'
    }

    repeat(100) {
        grid = grid.clone { x, y, e ->
            val around = around8(x, y).count { it == '#' }
            val isCorner = isStuck && (x == 0 && y == 0 || x == 0 && y == maxY() || x == maxX() && y == maxY() || x == 0 && y == maxY())
            when (e) {
                '#' -> if (around == 2 || around == 3 || isCorner) '#' else '.'
                else -> if (around == 3) '#' else '.'
            }
        }
    }
    return grid.all().count { it.v == '#' }
}
