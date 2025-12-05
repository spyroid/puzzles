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
    fun rolls() = grid.all()
        .filter { e -> e.v == '@' && grid.around8(e.p).filter { it.v == '@' }.size < 4 }

    val part1 = rolls().count()
    val part2 = generateSequence { rolls().onEach { grid[it.p] = '.' }.count() }
        .takeWhile { it > 0 }
        .sum()

    return part1 to part2
}
