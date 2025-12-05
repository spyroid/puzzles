package aoc.y2025.day5

import gears.mergeWith
import gears.puzzle

fun main() {
    puzzle {
        cafeteria(input())
    }
}

private fun cafeteria(input: String): Any {
    val (ranges, ids) = input.split("\n\n").let { (a, b) ->
        a.split("\n")
            .map { LongRange(it.substringBefore("-").toLong(), it.substringAfter("-").toLong()) }
            .sortedBy { longs -> longs.first } to b.split("\n").map { it.toLong() }
    }

    val part1 = ids.count { ranges.any { range -> it in range } }

    val part2 = sequence {
        var r = LongRange.EMPTY
        ranges.forEach { range ->
            if (r == LongRange.EMPTY) r = range else {
                val merged = r.mergeWith(range)
                if (merged == null) {
                    yield(r)
                    r = range
                } else {
                    r = merged
                }
            }
        }
        yield(r)
    }.sumOf { it.last - it.first + 1 }
    return part1 to part2
}
