package aoc.y2025.day7

import gears.puzzle

fun main() {
    puzzle {
        laboratories(inputLines())
    }
}

private fun laboratories(input: List<String>): Any {
    val cols = mutableSetOf(input.first().indexOf('S'))
    val part1 = input.filterIndexed { idx, _ -> idx % 2 == 0 }.sumOf { line ->
        (1..<line.length).filter { it in cols && line[it] == '^' }.onEach { col ->
            cols.remove(col)
            cols.add(col + 1)
            cols.add(col - 1)
        }.count()
    }

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

    return part1 to counters.sum()
}
