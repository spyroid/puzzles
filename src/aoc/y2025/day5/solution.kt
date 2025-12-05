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
            .map { line -> line.split("-").let { LongRange(it.first().toLong(), it.last().toLong()) } }
            .sortedBy { longs -> longs.first } to b.split("\n").map { it.toLong() }
    }

    val part1 = ids.count { ranges.any { range -> it in range } }

    val part2 = sequence {
        var current = ranges.first()
        ranges.drop(1).forEach { range ->
            current.mergeWith(range).let { merged ->
                if (merged == null) yield(current).also { current = range } else current = merged
            }
        }
        yield(current)
    }.sumOf { it.last - it.first + 1 }

    return part1 to part2
}
