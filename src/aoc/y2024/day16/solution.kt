package aoc.y2024.day16

import gears.inc
import gears.puzzle

fun main() {
    puzzle { xxx(inputLines()) }
}

private fun xxx(input: List<String>): Any {
    val grid = "absbdd".groupingBy { it }.eachCount().toMutableMap()
    println(grid)
    grid.inc('c', 3)
    println(grid)

    return input.size
}
