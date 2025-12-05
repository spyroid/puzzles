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
            .map { it.split("-").let { (a, b) -> LongRange(a.toLong(), b.toLong()) } }
            .sortedBy { longs -> longs.first } to b.split("\n").map { it.toLong() }
    }

    val part1 = ids.count { ranges.any { range -> it in range } }

    val part2 = sequence {
        var current = ranges.first()
        ranges.drop(1).forEach { range ->
            current.mergeWith(range).let { merged ->
                if (merged != null) current = merged else yield(current).also { current = range }
            }
        }
        yield(current)
    }.sumOf { it.last - it.first + 1 }

    return part1 to part2
}
