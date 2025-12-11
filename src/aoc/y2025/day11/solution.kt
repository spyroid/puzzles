package aoc.y2025.day11

import gears.puzzle

fun main() {
    puzzle {
        reactor(inputLines())
    }
}

private fun reactor(input: List<String>): Any {
    val devices = input.map { line -> line.split(": ") }.associate { (a, b) -> a to b.split(" ") }

    fun find(start: String, end: String, memo: MutableMap<String, Long> = mutableMapOf()): Long {
        return if (start == end) 1L else memo.getOrPut(start) {
            devices[start]?.sumOf { next -> find(next, end, memo) } ?: 0
        }
    }

    val part1 = find("you", "out")
    val part2 = find("svr", "fft") * find("fft", "dac") * find("dac", "out")

    return part1 to part2
}
