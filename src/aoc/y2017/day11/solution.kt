package aoc.y2017.day11

import gears.puzzle
import kotlin.math.abs

private fun main() {
    puzzle { hexed(inputLines().first().split(",")) }
}

private fun hexed(input: List<String>): Any {
    var p = Cube(0, 0, 0)
    var max = 0

    input.forEach {
        p = when (it) {
            "n" -> Cube(p.s + 1, p.q, p.r - 1)
            "s" -> Cube(p.s - 1, p.q, p.r + 1)
            "ne" -> Cube(p.s, p.q + 1, p.r - 1)
            "sw" -> Cube(p.s, p.q - 1, p.r + 1)
            "nw" -> Cube(p.s + 1, p.q - 1, p.r)
            "se" -> Cube(p.s - 1, p.q + 1, p.r)
            else -> Cube(0, 0, 0)
        }
        max = maxOf(max, p.dist())
    }
    return p.dist() to max
}

private data class Cube(val s: Int, val q: Int, val r: Int) {
    fun dist() = (abs(s) + abs(q) + abs(r)) / 2
}
