package aoc.y2017.day13

import gears.puzzle

private fun main() {
    puzzle { scanner(inputLines()) }
}

private fun scanner(input: List<String>): Any {
    val all = input.map { it.split(": ").map { a -> a.toInt() } }.associate { it.first() to Scanner(it.last()) }
    var severity = 0
    for (idx in 0..all.keys.max()) {
        val s = all[idx]
        if (s?.level == 0) severity += idx * s.size
        all.values.forEach { it.step() }
    }
    return severity
}

private data class Scanner(val size: Int, var level: Int = 0) {
    private var dir = 1
    fun step() {
        if (level + dir !in 0..<size) dir = -dir
        level += dir
    }
}
