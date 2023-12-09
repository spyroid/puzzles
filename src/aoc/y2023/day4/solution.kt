package aoc.y2023.day4

import gears.findInts
import gears.puzzle
import gears.splitBy

private fun main() {
    puzzle("parts 1 & 2") { scratch(inputLines()) }
}

private fun scratch(input: List<String>): Any {
    val cards = mutableMapOf<Int, Int>()
    return input.map { card ->
        val (id, a, b) = card.findInts().splitBy(setOf(1, 11))
        val matches = (a intersect b.toSet()).size
        val m = cards.compute(id.first()) { _, v -> (v ?: 0) + 1 } ?: 0
        (1..matches).forEach { i -> cards.compute(id.first() + i) { _, v -> (v ?: 0) + m } }
        1 shl matches - 1
    }.sumOf { it } to cards.values.sum()
}
