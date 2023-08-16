package aoc.y2016.day6

import gears.puzzle

private fun main() {
    puzzle { noise(linesFrom("input.txt")) }
    puzzle { noise(linesFrom("input.txt"), true) }
}

private fun noise(lines: List<String>, part2: Boolean = false): String {
    val all = Array(8) { mutableListOf<Char>() }
    lines.forEach { for (i in it.indices) all[i].add(it[i]) }
    return all.map { list ->
        val a = list.groupingBy { it }.eachCount().toList()
        if (part2) a.minBy { it.second }.first else a.maxBy { it.second }.first
    }.joinToString("")
}
