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

    val counters = LongArray(input.first().length) { 0 }.apply {
        this[input.first().indexOf('S')] = 1
    }

    input.filterIndexed { idx, _ -> idx % 2 == 0 }.forEach { line ->
        (1..<line.length).forEach { i ->
            if (line[i] == '^') {
                counters[i + 1] += counters[i]
                counters[i - 1] += counters[i]
                counters[i] = 0
            }
        }
    }

    return splitters.count() to counters.sum()
}
