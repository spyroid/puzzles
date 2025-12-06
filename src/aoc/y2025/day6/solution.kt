package aoc.y2025.day6

import gears.Grid
import gears.puzzle

fun main() {
    puzzle {
        trashCompactor(inputLines())
    }
}

private fun trashCompactor(input: List<String>): Any {
    val grid = input.map { line -> line.trim().split("\\s+".toRegex()) }
        .let { lists ->
            lists.first().indices.map { col ->
                val (numbers, operations) = mutableListOf<Long>() to mutableListOf<String>()
                lists.indices.forEach { row ->
                    val v = lists[row][col]
                    if (v.first().isDigit()) numbers.add(v.toLong()) else operations.add(v)
                }
                numbers to operations
            }
        }


    val part1 = grid.sumOf { (n, o) -> if (o.first() == "+") n.reduce { acc, lng -> acc + lng } else n.reduce { acc, lng -> acc * lng } }

    return part1
}
