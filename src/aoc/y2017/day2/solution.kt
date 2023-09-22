package aoc.y2017.day2

import gears.puzzle

private fun main() {
    puzzle { checksum(linesFrom("input.txt").asInts()) }
    puzzle { checksum2(linesFrom("input.txt").asInts()) }
}

private fun List<String>.asInts() = this.map { s -> s.split("\\s+".toRegex()).map { it.toInt() }.sortedDescending() }

private fun checksum(items: List<List<Int>>) = items.sumOf { it.first() - it.last() }
private fun checksum2(items: List<List<Int>>) = items.sumOf {
    for (i in it.indices) for (j in i + 1..it.lastIndex) if (it[i] % it[j] == 0) return@sumOf it[i] / it[j]
    0
}

