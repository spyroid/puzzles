package aoc.y2018.day13

import gears.Direction
import gears.Direction.*
import gears.Grid2
import gears.Point
import gears.puzzle


private fun main() {
    puzzle { madness(inputLines()) }
}

private fun madness(input: List<String>): Any {
    val mx = input.maxOf { it.length }
    val grid = Grid2.of(input.map { it.padEnd(mx, ' ') }) { it }
    val carts = grid.allPoints().mapNotNull { p ->
        when (val dir = Direction.of(grid.at(p) ?: return@mapNotNull null)) {
            UP, DOWN, LEFT, RIGHT -> {
                Cart(p, dir).also {
                    it.grid = grid
                    if (dir == LEFT || dir == RIGHT) grid[p] = '-'
                    if (dir == UP || dir == DOWN) grid[p] = '|'
                }
            }
            else -> null
        }
    }.toList()
    repeat(20) { carts.forEach { it.drive() } }
    return mx
}

private data class Cart(var p: Point, var dir: Direction) {
    lateinit var grid: Grid2<Char>
    var cross = 0
    var crossDir = 1

    fun drive() {
        p += dir
        val c = grid[p]
        if (c == '+') println("crossroad $p")
        when (c to dir) {
            '\\' to RIGHT, '/' to DOWN, '\\' to LEFT, '/' to UP -> dir = dir.turnCcw()
            '/' to LEFT, '\\' to DOWN, '/' to RIGHT, '\\' to UP -> dir = dir.turnCw()
        }
    }
}

