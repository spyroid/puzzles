package aoc.y2024.day11

import gears.puzzle

fun main() {
    puzzle { plutonianPebbles(input()) }
}

private fun plutonianPebbles(input: String): Any {

    fun blink(stones: Map<Long, Long>) = buildMap {
        for ((stone, count) in stones) {
            val str = stone.toString()
            when {
                stone == 0L -> merge(1, count) { a, b -> a + b }
                str.length % 2 != 0 -> merge(stone * 2024, count) { a, b -> a + b }
                else -> str.chunked(str.length / 2) { it.toString().toLong() }.forEach { merge(it, count) { a, b -> a + b } }
            }
        }
    }

    var stones = input.split(" ").map { it.toLong() }.associate { it to 1L }
    val arrangements = generateSequence(stones) { blink(it) }.drop(1)
    return arrangements.take(25).last().values.sum() to arrangements.take(75).last().values.sum()
}
