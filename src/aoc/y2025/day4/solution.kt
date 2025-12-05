package aoc.y2025.day4

import gears.Grid
import gears.puzzle

fun main() {
    puzzle {
        printingDepartment(inputLines())
    }
}

private fun printingDepartment(input: List<String>): Any {
    val grid = Grid.of(input) { it }
    val part1 = grid.all().filter { it.v == '@' }.count {
        grid.around8(it.p).filter { e -> e.v == '@' }.size < 4
    }

    return part1
}
