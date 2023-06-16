package aoc.y2020.day06

import gears.puzzle

private fun main() {
    puzzle { part1(linesFrom("test.txt")) }
    puzzle { part1(linesFrom("input.txt")) }
    puzzle { part2(linesFrom("test.txt")) }
    puzzle { part2(linesFrom("input.txt")) }
}

private fun part1(lines: List<String>): Int {
    return groups(lines).map { it.fold("") { acc, s -> acc + s }.toSet() }.map { it.size }.sum()
}

private fun part2(lines: List<String>): Int {
    return groups(lines).map { calc(it) }.sum()
}

private fun calc(group: List<String>): Int {
    val stats = group
        .fold("") { acc, s -> acc + s }
        .toList()
        .groupingBy { it }
        .eachCount()

    return stats.count { it.value >= group.size }
}

private fun groups(lines: List<String>) = sequence {
    val list = mutableListOf<String>()
    lines.forEach {
        if (it.isEmpty()) {
            yield(list)
            list.clear()
        } else {
            list.add(it)
        }
    }
    yield(list)
}

