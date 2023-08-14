package aoc.y2015.day20

import gears.puzzle

private fun main() {
    puzzle { infinite(29000000, 10, Int.MAX_VALUE) }
    puzzle { infinite(29000000, 11, 50) }
}

fun infinite(input: Int, visit: Int, maxValue: Int): Int {
    val house = Array(750_000) { 0 }
    for (i in 1..house.lastIndex) {
        for ((count, j) in (i..house.lastIndex step i).withIndex()) {
            house[j] += i * visit
            if (count > maxValue) break
        }
    }
    return house.indexOfFirst { it >= input }
}

