package aoc.y2020.day01

import gears.puzzle

private fun main() {
    puzzle { part1(linesFrom("test.txt").map { it.toLong() }.toList()) }
    puzzle { part1(linesFrom("input.txt").map { it.toLong() }.toList()) }
    puzzle { part2(linesFrom("test.txt").map { it.toLong() }.toList()) }
    puzzle { part2(linesFrom("input.txt").map { it.toLong() }.toList()) }
}

private fun part1(all: List<Long>): Long {
    for (i in all.indices) {
        for (j in i + 1..all.lastIndex) {
            if (all[i] + all[j] == 2020L) return all[i] * all[j]
        }
    }
    return 0
}

private fun part2(all: List<Long>): Long {
    for (i in all.indices) {
        for (j in i + 1..all.lastIndex) {
            for (k in j + 1..all.lastIndex) {
                if (all[i] + all[j] + all[k] == 2020L) return all[i] * all[j] * all[k]
            }
        }
    }
    return 0
}
