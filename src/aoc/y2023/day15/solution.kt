package aoc.y2023.day15

import gears.puzzle

private fun main() {
    puzzle("1 & 2") { lensLibrary(input()) }
}

private fun lensLibrary(input: String): Any {

    val s = input.split(",").sumOf { s -> s.fold(0L) { acc, c -> (acc + c.code) * 17 % 256 } }

    return s
}

