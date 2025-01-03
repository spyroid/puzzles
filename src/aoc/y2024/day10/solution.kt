package aoc.y2024.day10

import gears.Grid2
import gears.puzzle

fun main() {
    puzzle { hoofIt(inputLines()) }
}

private fun hoofIt(input: List<String>) = Grid2.of(input) { it.digitToInt() }.let { grid ->
    grid.all().filter { it.v == 0 }.map { trailhead ->
        val seen = mutableSetOf<Grid2.Entry<Int>>()
        val track = ArrayDeque(listOf(trailhead))
        var count = 0
        while (track.isNotEmpty()) {
            val current = track.removeFirst().also { seen.add(it) }
            val around = grid.around4(current.p).filter { it.v == current.v + 1 }.also { track.addAll(it) }
            if (around.isEmpty() && current.v == 9) count++
        }
        seen.count { it.v == 9 } to count
    }.unzip().let { (a, b) -> a.sum() to b.sum() }
}