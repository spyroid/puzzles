package aoc.y2021.day25

import gears.puzzle

fun main() {
    puzzle {
        val input = inputLines("input.txt")
        val height = input.size
        val width = input.first().length
        var area = Array(height) { y -> Array(width) { x -> input[y][x] } }

        fun move(ch: Char, dx: Int, dy: Int): Boolean {
            var moved = false
            val area1 = Array(height) { y -> Array(width) { x -> area[y][x] } }

            for (y in 0 until height) for (x in 0 until width) {
                if (area[y][x] != ch) continue
                val xx = (x + dx) % width
                val yy = (y + dy) % height
                if (area[yy][xx] == '.') {
                    area1[yy][xx] = ch
                    area1[y][x] = '.'
                    moved = true
                } else {
                    area1[y][x] = ch
                }
            }
            area = area1
            return moved
        }

        var count = 0
        do {
            count++
            val r1 = move('>', 1, 0)
            val r2 = move('v', 0, 1)
        } while (r1 || r2)
        count
    }
}
