package aoc.y2023.day11

import gears.puzzle
import kotlin.math.absoluteValue

private fun main() {
    puzzle("1") { cosmicExpansion(inputLines(), 1) }
    puzzle("2") { cosmicExpansion(inputLines(), 999_999) }
}

private fun cosmicExpansion(input: List<String>, multiplier: Long): Any {
    val ri = input.withIndex().filter { s -> s.value.all { it == '.' } }.map { it.index }.toSet()
    val ci = input.first().indices.asSequence().map { c -> input.map { it[c] } }.withIndex()
        .filter { l -> l.value.all { it == '.' } }.map { it.index }.toSet()

    val universe = input.indices.flatMap { r ->
        input[r].indices.filter { input[r][it] == '#' }.map { c ->
            val x = r + ri.count { r > it } * multiplier
            val y = c + ci.count { c > it } * multiplier
            Pair(x, y)
        }
    }

    return universe.flatMapIndexed { i, a -> universe.subList(i + 1, universe.size).map { b -> a to b } }
        .sumOf { (a, b) -> (b.first - a.first).absoluteValue + (b.second - a.second).absoluteValue }
}
