package aoc.y2024.day11

import gears.puzzle

fun main() {
    puzzle { plutonianPebbles(input()) }
}

private fun plutonianPebbles(input: String): Any {

    fun blink(stones: List<Long>) = buildList {
        stones.forEach { stone ->
            if (stone == 0L) {
                add(1)
            } else if (stone.toString().length % 2 == 0) {
                stone.toString().also {
                    add(it.substring(0, it.length / 2).toLong())
                    add(it.substring(it.length / 2).toLong())
                }
            } else {
                add(stone * 2024L)
            }
        }
    }

    var stones = input.split(" ").map { it.toLong() }
    repeat(25) {
        stones = blink(stones)
    }
    return stones.size
}
