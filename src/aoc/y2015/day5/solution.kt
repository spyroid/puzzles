package aoc.y2015.day5

import gears.puzzle

private fun main() {
    puzzle { part11(linesFrom("test.txt")) }
    puzzle { part11(linesFrom("input.txt")) }
    puzzle { part2(listOf("qjhvhtzxzqqjkmpb")) }
    puzzle { part2(linesFrom("input.txt")) }
}

private fun part1(all: List<String>): Int {
    return all
        .filter { line ->
            listOf("ab", "cd", "pq", "xy").none { line.contains(it) }
        }.count { line ->
            val counts = line.groupingBy { it }.eachCount()
            val a = counts.filterKeys { it in "aeiou" }.map { it.value }.sum() > 2
            val b = counts.keys.any { line.contains("$it$it") }
            a && b
        }
}

private val v11 = "[aeiou].*[aeiou].*[aeiou]".toRegex()
private val v12 = "(\\w)\\1".toRegex()
private val v13 = "ab|cd|pq|xy".toRegex()
private fun part11(all: List<String>) = all.count {
    v11.containsMatchIn(it) && v12.containsMatchIn(it) && !v13.containsMatchIn(it)
}

private val v21 = "(\\w\\w).*\\1".toRegex()
private val v22 = "(\\w).\\1".toRegex()
private fun part2(all: List<String>) = all.count { v21.containsMatchIn(it) && v22.containsMatchIn(it) }
