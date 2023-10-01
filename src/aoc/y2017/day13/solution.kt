package aoc.y2017.day13

import gears.puzzle

private fun main() {
    puzzle { scanner(inputLines()) }
}

private fun scanner(input: List<String>): Any {
    val all = input.map { it.split(": ").map { a -> a.toInt() } }.associate { it.first() to Scanner(it.last()) }
    fun severity(delay: Int): Int {
        var severity = 0
        all.values.forEach { it.reset() }
        repeat(delay) { all.values.forEach { it.step() } }
        for (idx in 0..all.keys.max()) {
            val s = all[idx]
            if (s?.level == 0) severity += idx * s.size
            all.values.forEach { it.step() }
        }
        return severity
    }
//    println(severity(0))
//    println(all)
    var i = 0
    val part1 = 99//severity(0)
    val list = mutableListOf<Int>()
    while (true) {
        val s = severity(i)
        if (s == 0) {
            list.add(i)
        }
        if (list.size > 1) break
        i++
    }
    return part1 to i
}

private data class Scanner(val size: Int, var level: Int = 0) {
    private var dir = 1
    fun step() {
        if (level + dir !in 0..<size) dir = -dir
        level += dir
    }
    fun reset() {
        dir = 1
        level = 0
    }
}
