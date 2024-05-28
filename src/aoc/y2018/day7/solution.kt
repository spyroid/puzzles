package aoc.y2018.day7

import gears.puzzle

private fun main() {
    puzzle { theSumOfItsParts(inputLines()) }
}

private fun theSumOfItsParts(lines: List<String>): Any {
    var pairs = lines.map { it.split(" ") }.map { it[7] to it[1] }.toList()
    val steps = pairs.flatMap { it.toList() }.toMutableSet()

    val path = mutableListOf<String>()
    while (steps.isNotEmpty()) {
        val next = steps.filter { s -> pairs.all { it.first != s } }.minOf { it }
        path.add(next).also { steps.remove(next) }
        pairs = pairs.filter { it.second != next }
    }
    return path.joinToString("")
}