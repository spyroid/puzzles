package aoc.y2015.day10

import gears.puzzle

private fun main() {
    puzzle { part1("1113122113", 40) }
    puzzle { part1("1113122113", 50) }
}

private fun part1(line: String, repeat: Int): Int {
    return generateSequence(line) { s -> build(s) }.take(repeat + 1).last().length
}

private fun build(from: String): String {
    var prev = from.first()
    var size = 1
    return buildString {
        for (c in from.drop(1)) {
            if (c == prev) {
                size +=1
            } else {
                append("$size$prev")
                prev = c
                size = 1
            }
        }
        append("$size$prev")
    }
}
