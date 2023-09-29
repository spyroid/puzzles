package aoc.y2017.day9

import gears.puzzle

private fun main() {
    puzzle { stream(input()) }
}

private fun stream(input: String): Any {
    var garbage = 0
    var s = input.replace("!\\S".toRegex(), "")
    while (s.contains('<')) s = s.removeRange(s.indexOf('<'), s.indexOf('>') + 1).also { garbage += s.length - it.length - 2 }
    var stack = 0
    val sum = s.filter { it != ',' }.sumOf { c -> if (c == '{') stack++.let { 0 } else stack-- }
    return sum to garbage
}
