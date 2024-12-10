package aoc.y2024.day10

import gears.Grid
import gears.puzzle

fun main() {
    puzzle { hoofIt(inputLines()) }
}

private fun hoofIt(input: List<String>): Any {
    val grid = Grid.of(input) { if (it == '.') -1 else it.digitToInt() }

    val (p1, p2) = grid.all().filter { it.v == 0 }.map { t ->
        val seen = mutableSetOf<Grid.GValue<Int>>()
        var cross = 0
        val track = ArrayDeque<Grid.GValue<Int>>().apply { add(t) }
        while (track.isNotEmpty()) {
            val current = track.removeFirst()
            seen.add(current)
            val around = grid.gvAround(current.p).filter { it.v == current.v + 1 }
            if (around.isEmpty() && current.v == 9) cross++
            track.addAll(around)
        }
        seen.count { it.v == 9 } to cross
    }.unzip().let{ (a, b) -> a.sum() to b.sum() }

    return p1 to p2
}