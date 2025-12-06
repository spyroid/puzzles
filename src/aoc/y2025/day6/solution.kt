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
    val grids = input.dropLast(1).let { strings ->
        strings.first().indices.map { col ->
            strings.indices.map { row -> strings[row][col] }.joinToString("")
        }.splitByElement { it.isBlank() }.map { lines -> Grid.of(lines) { it } }
    }
    val ops = input.last().trim().split("\\s+".toRegex())

    val part1 = grids.withIndex().sumOf { (i, grid) ->
        grid.rotateClockwise().flipY().rows().map { row -> row.map { it.v }.joinToString("").trim().toLong() }
            .reduce { a, b -> if (ops[i] == "+") a + b else a * b }
    }

    val part2 = grids.withIndex().sumOf { (i, grid) ->
        grid.rows().map { row -> row.map { it.v }.joinToString("").trim().toLong() }
            .reduce { a, b -> if (ops[i] == "+") a + b else a * b }
    }

    return part1 to part2
}
