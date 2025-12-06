package aoc.y2025.day6

import gears.Grid
import gears.puzzle
import gears.splitByElement

fun main() {
    puzzle {
        trashCompactor(inputLines())
    }
}

private fun trashCompactor(input: List<String>): Any {
    val grids = input.first().indices.map { col ->
        input.dropLast(1).indices.map { row -> input[row][col] }.joinToString("")
    }.splitByElement { it.isBlank() }.map { lines -> Grid.of(lines) { it } }
    val ops = input.last().trim().split("\\s+".toRegex())

    fun rowToLong(row: List<Grid.Entry<Char>>) = row.map { it.v }.joinToString("").trim().toLong()
    fun calculate(a: Long, b: Long, op: String) = if (op == "+") a + b else a * b
    fun partX(mapper: (Grid<Char>) -> Grid<Char>) = grids.withIndex().sumOf { (i, grid) ->
        mapper(grid).rows().map { rowToLong(it) }.reduce { a, b -> calculate(a, b, ops[i]) }
    }

    return partX { it.rotateClockwise().flipY() } to partX { it }
}
