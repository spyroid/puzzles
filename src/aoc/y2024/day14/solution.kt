package aoc.y2024.day14

import gears.Grid2
import gears.findInts
import gears.puzzle

fun main() {
    puzzle { restroomRedoubt(inputLines()) }
}

private fun restroomRedoubt(input: List<String>): Any {
    val robots = input.map { it.findInts() }
    var seconds = 0
    var (p1, p2) = 0 to 0
    var (w, h) = 101 to 103

    fun safetyFactor(grid: Grid2<Int>): Int {
        val (mx, my) = w / 2 to h / 2
        val quadrants = mutableListOf(0, 0, 0, 0)

        grid.all().forEach { (p, v) ->
            if (p.x != mx && p.y != my) {
                val q = (if (p.x < mx) 0 else 1) + (if (p.y < my) 0 else 2)
                quadrants[q] = quadrants[q] + v
            }
        }
        return quadrants.fold(1) { a, b -> a * b }
    }

    fun xmasTree(grid: Grid2<Int>): Boolean {
        grid.data().forEach {
            var c = 0
            for (v in it) {
                if (v == 0) c = 0
                if (++c > 10) return true
            }
        }
        return false
    }

    while (p1 == 0 || p2 == 0) {
        val grid = Grid2.of(w, h, 0)
        robots.forEach { (px, py, vx, vy) ->
            var nx = (px + vx * seconds) % w
            var ny = (py + vy * seconds) % h
            if (nx < 0) nx += w
            if (ny < 0) ny += h
            grid[nx, ny] = grid[nx, ny] + 1
        }
        if (seconds == 100) p1 = safetyFactor(grid)
        if (p2 == 0 && xmasTree(grid)) {
            p2 = seconds
            println(grid)
        }
        seconds++
    }

    return p1 to p2
}

