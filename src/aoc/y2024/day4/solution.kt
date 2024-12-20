package aoc.y2024.day4

import gears.Direction
import gears.Grid
import gears.puzzle

fun main() {
    puzzle { ceresSearch(inputLines()) }
}

private fun ceresSearch(input: List<String>): Any {
    val grid = Grid.of(input) { it }

    fun check(gv: Grid.Entry<Char>) = Direction.entries.count { dir ->
        generateSequence(gv.p) { it + dir }
            .take(4)
            .map { grid.at(it) }
            .joinToString("") == "XMAS"
    }

    fun check2(gv: Grid.Entry<Char>) = listOf(Direction.DOWN_RIGHT, Direction.DOWN_LEFT)
        .all { dir ->
            generateSequence(gv.p - dir) { it + dir }
                .take(3)
                .mapNotNull { grid.at(it) }
                .joinToString("")
                .let { it == "MAS" || it == "SAM" }
        }.compareTo(false)

    val p1 = grid.all().filter { it.v == 'X' }.sumOf { check(it) }
    val p2 = grid.all().filter { it.v == 'A' }.sumOf { check2(it) }

    return p1 to p2
}