package aoc.y2016.day3

import gears.puzzle

private fun main() {
    puzzle { threeSides(linesFrom("input.txt")) }
    puzzle { threeSides(linesFrom("input.txt"), true) }
}

private fun threeSides(lines: List<String>, part2: Boolean = false): Int {
    return sequence {
        if (part2) {
            lines.windowed(3, 3).forEach { w ->
                val lists = listOf(mutableListOf<Int>(), mutableListOf(), mutableListOf())
                w.forEach { parse(it).forEachIndexed { i, v -> lists[i].add(v) } }
                yieldAll(lists)
            }
        } else lines.forEach { yield(parse(it)) }
    }.count { (a, b, c) -> a + b > c && b + c > a && a + c > b }
}

private fun parse(s: String) = s.trim().split("\\s+".toRegex()).map { it.toInt() }
