package aoc.y2024.day19

import gears.puzzle

fun main() {
    puzzle { linenLayout(inputLines()) }
}

private fun linenLayout(input: List<String>): Any {
    val (towels, designs) = input.first().split(", ") to input.drop(2)
    val cache = mutableMapOf("" to 1L)
    fun count(design: String): Long {
        cache[design]?.let { return it }
        return towels.sumOf { if (design.startsWith(it)) count(design.removePrefix(it)) else 0 }
            .also { cache[design] = it }
    }

    val possibilities = designs.map { count(it) }
    return possibilities.count { it > 0 } to possibilities.sumOf { it }
}
