package aoc.y2020.day09

import gears.puzzle

private fun main() {
    puzzle { part1(linesFrom("test.txt").asLongs(), 5) }
    puzzle { part1(linesFrom("input.txt").asLongs(), 25) }
    puzzle { part2(linesFrom("test.txt").asLongs(), 5) }
    puzzle { part2(linesFrom("input.txt").asLongs(), 25) }
}

private fun part1(lines: List<Long>, preamble: Int): Long {
    for (i in preamble + 1..lines.lastIndex) {
        val v = lines.asSums(i - preamble..<i).contains(lines[i])
        if (!v) return lines[i]
    }
    return 0
}

private fun part2(lines: List<Long>, preamble: Int): Long {
    val invalid = part1(lines, preamble)
    val range = lines.findRange(invalid).sorted()
    return range.first() + range.last()
}

private fun List<Long>.findRange(value: Long): List<Long> {
    for (i in 0..size - 2) {
        for (j in i + 2..lastIndex) {
            val sum = subList(i, j).sum()
            if (sum == value) return subList(i, j)
            if (sum > value) break
        }
    }
    return listOf()
}

private fun List<String>.asLongs() = this.map { it.toLong() }

private fun List<Long>.asSums(range: IntRange): Set<Long> {
    val set = mutableSetOf<Long>()
    for (i in range) {
        for (j in i + 1..range.last) set.add(this[i] + this[j])
    }
    return set
}

