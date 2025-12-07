package aoc.y2025.day7

import gears.Direction
import gears.Grid
import gears.puzzle

fun main() {
    puzzle {
        laboratories(inputLines())
    }
}

private fun laboratories(input: List<String>): Any {
    val grid = Grid.of(input) { it }

    val part1 = grid.rows().dropLast(1).sumOf { entries ->
        entries.map { entry ->
            when (entry.v) {
                'S' -> grid.set(entry.p + Direction.UP, '|')
                '|' -> grid.get(entry.p + Direction.UP)?.let { x ->
                    when (x.v) {
                        '.' -> grid.set(entry.p + Direction.UP, '|')
                        '^' -> grid.set(entry.p + Direction.UP_LEFT, '|')
                            .also { grid.set(entry.p + Direction.UP_RIGHT, '|') }
                            .also { return@map 1 }
                    }
                }
            }
            0
        }.sum()
    }

    return part1
}
