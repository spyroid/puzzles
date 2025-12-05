package aoc.y2025.day5

import gears.puzzle

fun main() {
    puzzle {
        cafeteria(input())
    }
}

private fun cafeteria(input: String): Any {
    val (ranges, ids) = input.split("\n\n").let { (a, b) ->
        a.split("\n").map { LongRange(it.substringBefore("-").toLong(), it.substringAfter("-").toLong()) } to
                b.split("\n").map { it.toLong() }
    }

    val part1 = ids.count { ranges.any { range -> it in range } }

    return part1
}
