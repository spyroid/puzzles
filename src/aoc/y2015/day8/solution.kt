package aoc.y2015.day8

import gears.puzzle

private fun main() {
    puzzle { part1(inputLines("test.txt")) }
    puzzle { part1(inputLines("input.txt")) }
    puzzle { part2(inputLines("input.txt")) }
}

private fun part1(lines: List<String>): Int {
    return lines.sumOf { line ->
        line.length - line
            .replace("\\\\", "^")
            .replace(Regex("[^\\\\]\\\\x\\w{2}")) { "${it.value[0]}_" }
            .replace(Regex("[^\\\\]\\\\x\\w{2}")) { "${it.value[0]}_" }
            .replace("\\\"", "\"")
            .replace("^", "\\")
            .drop(1).dropLast(1).length
    }
}

private fun part2(lines: List<String>): Int {
    return lines.sumOf { line ->
        line.sumOf { c -> if (c == '\\' || c == '\"') 2 else 1 as Int } + 2 - line.length
    }
}
