package aoc.y2024.day10

import gears.Grid
import gears.puzzle

fun main() {
    puzzle { hoofIt(inputLines()) }
}

private fun hoofIt(input: List<String>): Any {
    val grid = Grid.of(input) { it.digitToInt() }
    return grid.all().filter { it.v == 0 }.map { t ->
        val seen = mutableSetOf<Grid.GValue<Int>>()
        val track = ArrayDeque<Grid.GValue<Int>>().apply { add(t) }
        var count = 0
        while (track.isNotEmpty()) {
            val current = track.removeFirst().also { seen.add(it) }
            val around = grid.gvAround(current.p).filter { it.v == current.v + 1 }.also { track.addAll(it) }
            if (around.isEmpty() && current.v == 9) count++
        }
        seen.count { it.v == 9 } to count
    }.unzip().let { (a, b) -> a.sum() to b.sum() }
}