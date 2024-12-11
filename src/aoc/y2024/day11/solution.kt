package aoc.y2024.day11

import gears.puzzle

fun main() {
    puzzle { plutonianPebbles(input()) }
}

private fun plutonianPebbles(input: String): Any {

    fun blink(stones: Map<Long, Long>) = buildMap {
        for ((stone, count) in stones) {
            val (str, len) = stone.toString().let { it to it.length }
            when {
                stone == 0L -> this[1] = getOrDefault(1, 0L) + count
                len % 2 == 0 -> {
                    val a = str.substring(0, len / 2).toLong()
                    val b = str.substring(len / 2).toLong()
                    this[a] = (this[a] ?: 0) + count
                    this[b] = (this[b] ?: 0) + count
                }

                else -> {
                    val y = stone * 2024
                    put(y, getOrDefault(y, 0L) + count)
                }
            }
        }
    }

    var stones = input.split(" ").map { it.toLong() }.associate { it to 1L }
    val arrangements = generateSequence(stones) { blink(it) }
    return arrangements.take(26).last().values.sum() to arrangements.take(76).last().values.sum()
}
