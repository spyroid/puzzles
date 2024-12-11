package aoc.y2024.day11

import gears.puzzle

fun main() {
    puzzle { plutonianPebbles(input()) }
}

private fun plutonianPebbles(input: String): Any {

    fun blink(stones: Map<Long, Long>) = buildMap {
        for ((stone, count) in stones) {
            val (str, mid) = stone.toString().let { it to it.length / 2 }
            when {
                stone == 0L -> merge(1, count) { a, b -> a + b }
                mid * 2 == str.length -> {
                    merge(str.substring(0, mid).toLong(), count) { a, b -> a + b }
                    merge(str.substring(mid).toLong(), count) { a, b -> a + b }
                }

                else -> merge(stone * 2024, count) { a, b -> a + b }
            }
        }
    }

    var stones = input.split(" ").map { it.toLong() }.associate { it to 1L }
    val arrangements = generateSequence(stones) { blink(it) }
    return arrangements.take(26).last().values.sum() to arrangements.take(76).last().values.sum()
}
