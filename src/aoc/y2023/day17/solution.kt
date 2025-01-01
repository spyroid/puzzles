package aoc.y2023.day17

import gears.Grid2
import gears.Point
import gears.puzzle
import kotlin.math.absoluteValue

private fun main() {
    puzzle("1 & 2") { clumsyCrucible(inputLines()) }
}

private fun clumsyCrucible(input: List<String>): Any {
    fun walk(a: Int, b: Int, firstTurn: Int): Int {
        val grid = Grid2.of(input) { mutableListOf(0, 0, it.digitToInt()) }
        val cursors = ArrayDeque<Pair<Point, Int>>().apply {
            add(Point.zero to 0)
            add(Point.zero to 1)
        }

        fun step(cursor: Pair<Point, Int>, range: IntProgression) {
            val (p, dir) = cursor
            val newDir = 1 - dir
            val block = grid[p]
            val (dx, dy) = if (dir == 0) 1 to 0 else 0 to 1
            var newLoss = block[dir]
            for (i in range) {
                val newX = cursor.first.x + dx * i
                val newY = cursor.first.y + dy * i
                val newBlock = grid.at(newX, newY) ?: continue
                newLoss += newBlock[2]
                if (newBlock[newDir] == 0 || newBlock[newDir] > newLoss) {
                    if (i.absoluteValue >= firstTurn) {
                        newBlock[newDir] = newLoss
                        cursors.add(Point(newX, newY) to newDir)
                    }
                }
            }
        }

        while (cursors.isNotEmpty()) {
            val cursor = cursors.removeFirst()
            step(cursor, a..b)
            step(cursor, -a downTo -b)
        }
        return grid[grid.maxX, grid.maxY].dropLast(1).min()
    }

    return walk(1, 3, 1) to walk(1, 10, 4)
}
