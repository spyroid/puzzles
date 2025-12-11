package aoc.y2025.day11

import gears.puzzle

fun main() {
    puzzle {
        reactor(inputLines())
    }
}

private fun reactor(input: List<String>): Any {
    val devices = input.map { line -> line.split(": ") }.associate { (a, b) -> a to b.split(" ") }
    fun find(key: String): Long = if (key == "out") 1 else devices.getValue(key).sumOf { find(it) }
    val part1 = find("you")

    return part1
}
