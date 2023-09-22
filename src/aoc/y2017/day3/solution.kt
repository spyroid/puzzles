package aoc.y2017.day3

import gears.puzzle
import kotlin.math.absoluteValue
import kotlin.math.sqrt

private fun main() {
    puzzle { spiral(368078) }
    puzzle { spiral11(22) }
}

fun spiral11(input: Int) {
    println(sqrt(input.toDouble()))
}

private fun spiral(input: Int): Int {
    val (side, level) = generateSequence(1) { it + 2 }.first { input < it * it }.let { it to (it - 1) / 2 }
    val a = generateSequence(side * side) { it - side + 1 }.takeWhile { it + side - 1 > input }.last()
    val offset = input - a - (side / 2)
    return offset.absoluteValue + level
}
