package aoc.y2015.day24

import gears.Grid3
import gears.Point
import gears.inc
import gears.puzzle

fun main() {
    puzzle { xxx(inputLines()) }
}

private fun xxx(input: List<String>): Any {

    val grid = Grid3.of(input) { it }

    println(grid)

    val ggg = grid.clone { e -> if (e.v == 'Q') 1 else 0 }

    println()
    println(ggg)
    ggg.inc(Point(0, 0))
    ggg.inc(Point(-1, 1))
    println()
    println(ggg)

    return 0
}

