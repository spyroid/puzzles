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
        id to cubes
    }
    val possible = draws.filter { g ->
        g.second.all { c ->
            when (c.first) {
                "red" -> c.second <= 12
                "green" -> c.second <= 13
                else -> c.second <= 14
            }
        }
    }
    val powers = draws.map { d ->
        d.second.groupBy({ it.first }, { it.second })
            .mapValues { it.value.max() }
            .values.fold(1) { a, b -> a * b }
    }
    return possible.sumOf { it.first } to powers.sum()
}
