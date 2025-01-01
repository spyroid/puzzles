package aoc.y2023.day3

import gears.Grid2
import gears.Point
import gears.puzzle

fun main() {
    puzzle { gear(inputLines()) }
}

private fun gear(input: List<String>): Any {
    val grid = Grid2.of(input) { it }
    var number = ""
    val around = mutableSetOf<Char>()
    val gears = mutableSetOf<Point>()
    val g2n = mutableMapOf<Point, MutableSet<Int>>()
    return grid.all().mapNotNull { (p, c) ->
        if (c.isDigit()) {
            around.addAll(p.around8().mapNotNull {
                grid.at(it).also { c -> if (c == '*') gears.add(it) }
            }).also { number += c }
        } else if (number.isNotEmpty()) {
            val res = if (around.any { it != '.' && !it.isDigit() }) {
                gears.onEach { g2n.getOrPut(it) { mutableSetOf() }.add(number.toInt()) }.clear()
                number.toInt()
            } else null
            around.clear().also { number = "" }
            return@mapNotNull res
        }
        null
    }.sumOf { it } to g2n.values.filter { it.size == 2 }.sumOf { it.first() * it.last() }
}
