package aoc.y2018.day13

import gears.Direction
import gears.Direction.*
import gears.Grid
import gears.Point
import gears.puzzle


private fun main() {
    puzzle { madness(inputLines()) }
}

private fun madness(input: List<String>): Any {
    val mx = input.maxOf { it.length }
    val grid = Grid.of(input.map { it.padEnd(mx, ' ') }) { it }
    val carts = grid.allPoints().mapNotNull { p ->
        when (val dir = Direction.of(grid.at(p) ?: return@mapNotNull null)) {
            UP, DOWN, LEFT, RIGHT -> {
                Cart(p, dir).also {
                    if (dir == LEFT || dir == RIGHT) grid[p] = '-'
                    if (dir == UP || dir == DOWN) grid[p] = '|'
                }
            }

            else -> null
        }
    }.toList()
    println(carts)
    println(grid)
    repeat(10) {
        carts.forEach { grid.at(it.p)?.let { it1 -> it.drive(it1) } }
    }
    println(carts)

    return mx
}

private data class Cart(var p: Point, var dir: Direction) {
    var meters = 0

    fun drive(c: Char) {
        if (c == '+') println("crossroad $p")
        dir = when (c to dir) {
            '\\' to RIGHT, '/' to DOWN, '\\' to LEFT, '/' to UP -> dir.turnCw()
            '/' to LEFT, '\\' to DOWN, '/' to RIGHT, '\\' to UP -> dir.turnCcw()
            else -> dir
        }
        p += dir
    }
}
