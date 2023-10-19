package aoc.y2018.day18

import gears.Grid
import gears.puzzle

private fun main() {
    puzzle {
        pole(inputLines())
    }
}

private fun pole(input: List<String>): Any {
    var grid = Grid.of(input) { it }
    repeat(10) {
        grid = grid.clone { x, y, e ->
            val around = grid.around8(x, y)
            when (e) {
                '.' -> if (around.count { it == '|' } >= 3) '|' else '.'
                '|' -> if (around.count { it == '#' } >= 3) '#' else '|'
                '#' -> if (around.count { it == '#' } >= 1 && around.count { it == '|' } >= 1) '#' else '.'
                else -> '.'
            }
        }
    }
    return grid.all().count { it == '|' } * grid.all().count { it == '#' }
}
