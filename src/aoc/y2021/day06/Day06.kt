package aoc.y2021.day06

import gears.puzzle

fun main() {
    puzzle { part1(inputLines("input.txt").first(), 80) }
    puzzle { part1(inputLines("input.txt").first(), 256) }
}

private fun liveOneDay(map: Map<Int, Long>): Map<Int, Long> {
    return map
        .mapKeys { it.key - 1 }
        .toMutableMap()
        .also {
            val m1 = it.getOrDefault(-1, 0)
            it.merge(6, m1) { a, b -> a + b }
            it.merge(8, m1) { a, b -> a + b }
            it.remove(-1)
        }
}

private fun part1(seq: String, days: Int): Long {
    var mm = inputToMap(seq)
    return repeat(days) {
        mm = liveOneDay(mm)
    }.let {
        mm.values.sum()
    }
}

private fun inputToMap(str: String): Map<Int, Long> {
    return str.split(",")
        .map { it.toInt() }
        .groupingBy { it }
        .eachCount()
        .mapValues { (_, v) -> v.toLong() }
}
