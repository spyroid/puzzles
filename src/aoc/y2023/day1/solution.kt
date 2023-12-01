package aoc.y2023.day1

import gears.puzzle

private fun main() {
    puzzle { part1(inputLines()) }
    puzzle { part2(inputLines()) }
}

private fun part1(lines: List<String>) = lines.sumOf { s ->
    s.filter { it.isDigit() }.let { it.first().digitToInt() * 10 + it.last().digitToInt() }
}

private fun part2(lines: List<String>): Any {
    val nums = listOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    val res = lines.map { s ->
        s.fold(Pair(s, "")) { p, c ->
            var ss = ""
            val ni = nums.indexOfFirst { p.first.startsWith(it) }
            if (ni >= 0) ss = ni.toString()
            if (c.isDigit()) ss = c.toString()
            Pair(p.first.drop(1), p.second + ss)
        }.second
    }
    return part1(res)
}
