package aoc.y2023.day3

import gears.Grid
import gears.puzzle

private fun main() {
    puzzle { gear(inputLines()) }
}

private fun gear(input: List<String>): Any {
    val grid = Grid.of(input) { it }
    var number = ""
    val around = mutableSetOf<Char>()
    return grid.all().mapNotNull { (p, c) ->
        if (c.isDigit()) {
            around.addAll(p.around8().mapNotNull { grid.at(it) }).also { number += c }
        } else if (number.isNotEmpty()) {
            val res = if (around.any { it != '.' && !it.isDigit() }) number else null
            around.clear().also { number = "" }
            return@mapNotNull res
        }
        null
    }.sumOf { it.toInt() }
}
