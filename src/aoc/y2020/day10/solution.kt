package aoc.y2020.day10

import gears.puzzle
import gears.toInts

private fun main() {
    puzzle { part1(inputLines("test.txt").toInts()) }
    puzzle { part1(inputLines("input.txt").toInts()) }
    puzzle { part2(inputLines("test.txt").toInts()) }
    puzzle { part2(inputLines("input.txt").toInts()) }
}

private fun part1(input: List<Int>): Int {
    val s = input.sorted()
    val all = listOf(0).plus(s).plus(s.last() + 3)
    return all.zipWithNext()
        .map { it.second - it.first }
        .groupingBy { it }
        .eachCount()
        .let { it[1]!! * it[3]!! }
}

private fun part2(input: List<Int>): Long {
    val k = longArrayOf(1, 0, 0, 0)
    input.sorted().fold(0) { a, b ->
        val d = b - a
        k.copyInto(k, d, 0, k.size - d)
        k.fill(0, 0, d)
        k[0] += k.sum()
        b
    }
    return k[0]
}
