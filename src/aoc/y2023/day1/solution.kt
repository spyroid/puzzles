package aoc.y2023.day1

import gears.puzzle

private fun main() {
    puzzle { part1(inputLines()) }
    puzzle { part2(inputLines()) }
}

private fun part1(lines: List<String>) = lines.sumOf { s ->
    s.filter { it.isDigit() }.let { "${it.first()}${it.last()}" }.toInt()
}

private fun part2(lines: List<String>): Any {
    val nums = listOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    return lines.map { s ->
        s.fold(Pair(s, "")) { p, c ->
            val ni = nums.indexOfFirst { p.first.startsWith(it) }
            val ss = if (ni >= 0) ni.toString() else c.toString()
            Pair(p.first.drop(1), p.second + ss)
        }.second
    }.let { part1(it) }
}
