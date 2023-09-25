package aoc.y2017.day3

import gears.Direction.*
import gears.Point
import gears.puzzle
import kotlin.math.absoluteValue

private fun main() {
    puzzle { spiral(368078) }
    puzzle { spiral2(368078) }
}

fun spiral2(input: Int): Int {
    val map = mutableMapOf(Point(0, 0) to 1)
    var sideLength = 3
    var p = Point(1, 0)
    while (true) {
        for ((length, dir) in listOf(sideLength - 2 to UP, sideLength - 1 to LEFT, sideLength - 1 to DOWN, sideLength to RIGHT)) {
            repeat(length) {
                val v = p.around8().sumOf { map[it] ?: 0 }
                if (v > input) return v else map[p] = v
                p += dir.asPoint()
            }
        }
        sideLength += 2
    }
}

private fun spiral(input: Int): Int {
    val (side, level) = generateSequence(1) { it + 2 }.first { input < it * it }.let { it to (it - 1) / 2 }
    val a = generateSequence(side * side) { it - side + 1 }.takeWhile { it + side - 1 > input }.last()
    val offset = input - a - (side / 2)
    return offset.absoluteValue + level
}
