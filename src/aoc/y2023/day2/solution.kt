package aoc.y2023.day2

import gears.puzzle

private fun main() {
    puzzle { conundrum(inputLines()) }
}

private fun conundrum(lines: List<String>): Any {
    val draws = lines.map { s ->
        val (a, b) = s.split(":")
        val id = a.drop(5).toInt()
        val cubes = b.drop(1).replace(";", ",").split(", ")
            .map { it.split(" ").let { a -> a.last() to a.first().toInt() } }
        id to cubes.groupBy({ it.first }, { it.second }).mapValues { it.value.max() }
    }
    val possibles = draws.filter {
        it.second.all { c ->
            when (c.key) {
                "red" -> c.value <= 12
                "green" -> c.value <= 13
                else -> c.value <= 14
            }
        }
    }.sumOf { it.first }
    return possibles to draws.sumOf { it.second.values.fold(1L) { a, b -> a * b } }
}
