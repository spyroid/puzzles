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

    val splitters = grid.rows().dropLast(1).map { entries ->
        entries.mapNotNull { entry ->
            when (entry.v) {
                'S' -> grid[entry.p + Direction.UP] = '|'
                '|' -> grid[entry.p + Direction.UP]?.let { x ->
                    when (x.v) {
                        '.' -> grid[entry.p + Direction.UP] = '|'
                        '^' -> grid.set(entry.p + Direction.UP_LEFT, '|')
                            .also { grid[entry.p + Direction.UP_RIGHT] = '|' }
                            .also { return@mapNotNull x }
                    }
                }
            }
            null
        }
    }.flatten()

    return splitters.count()
}
