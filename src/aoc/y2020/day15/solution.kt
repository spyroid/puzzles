package aoc.y2020.day15

import gears.puzzle

private fun main() {
    val test = listOf(0, 3, 6)
    val input = listOf(13, 16, 0, 12, 15, 1)

    puzzle { part1(test, 2020) }
    puzzle { part1(input, 2020) }
    puzzle { part1(test, 30000000) }
    puzzle { part1(input, 30000000) }
}

private fun part1(input: List<Int>, limit: Long): Int {
    val history = mutableMapOf<Int, ArrayDeque<Int>>()
    input.forEachIndexed { i, v -> history.getOrPut(v) { ArrayDeque() }.add(i) }

    var current = input.last()
    var iter = input.size

    while (iter < limit) {
        val pr = history[current]
        current = if (pr == null || pr.size < 2) 0 else pr.last() - pr[pr.lastIndex - 1]
        history.getOrPut(current) { ArrayDeque() }.add(iter)
        iter += 1
    }
    return current
}
