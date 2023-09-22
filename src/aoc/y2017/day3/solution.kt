package aoc.y2017.day3

import gears.puzzle
import kotlin.math.absoluteValue

private fun main() {
    puzzle { spiral(368078) }
}

private fun spiral(input: Int): Int {
    var side = 1
    while (true) if (input < side.times(side)) break else side += 2
    val level = (side - 1) / 2
    var a = side * side
    while (a > input) a -= side - 1
    val offset = input - a - (side / 2)
    return offset.absoluteValue + level
}
