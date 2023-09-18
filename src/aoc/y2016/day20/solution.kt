package aoc.y2016.day20

import gears.puzzle

private fun main() {
    puzzle { wall(linesFrom("input.txt")) }
}

private fun wall(lines: List<String>): Long {
    val all = lines
        .map { s -> s.split('-').map { it.toLong() }.let { it[0]..it[1] } }
        .sortedBy { it.first }

    var lowest = 0L

    for (r in all) {
        if (r.first > lowest + 1) {
            break
        } else {
            lowest = maxOf(lowest, r.last)
        }
    }
    return lowest + 1
}


