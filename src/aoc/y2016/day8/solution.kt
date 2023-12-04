package aoc.y2016.day8

import gears.Grid
import gears.fullBlock
import gears.puzzle

private fun main() {
    puzzle { factor(linesFrom("input.txt")) }
}

private fun factor(lines: List<String>): Int {
    val grid = Grid.of(50, 6, '.')
    for (line in lines) {
        val (op, a, b, _, d) = line.split(" ").plus(listOf(".", ".", ".", "."))
        when (op) {
            "rect" -> a.split("x").also {
                for (x in 0..<it[0].toInt()) for (y in 0..<it[1].toInt()) grid[x, y] = fullBlock
            }

            "rotate" -> {
                val xy = b.substringAfter("=").toInt()
                when (a) {
                    "row" -> grid.rotateRowRight(xy, d.toInt())
                    "column" -> grid.rotateColDown(xy, d.toInt())
                }
            }
        }
    }
    println(grid)
    return grid.all().count { it.v == fullBlock }
}
