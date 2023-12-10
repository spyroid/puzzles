package aoc.y2016.day20

import gears.puzzle

private fun main() {
    puzzle { wall(inputLines("input.txt")) }
}

private fun wall(lines: List<String>): Pair<Long, Long> {
    var (a, b) = 0L to 0L
    val ranges = mutableListOf<LongRange>()
    lines.map { s -> s.split('-').map { it.toLong() }.let { it[0] to it[1] } }
        .sortedBy { it.first }
        .forEach { range ->
            if (range.first > b + 1) {
                ranges.add(a..b)
                a = range.first
                b = range.second
            } else {
                b = maxOf(b, range.second)
            }
        }
    ranges.add(a..b)

    val allowed = ranges.fold(4294967296) { acc, range -> acc - (range.last - range.first + 1) }
    return (ranges.first().last + 1) to allowed
}
