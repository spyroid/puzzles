package aoc.y2023.day16

import gears.Direction
import gears.Direction.*
import gears.puzzle
import kotlin.math.max

private fun main() {
    puzzle("1 & 2") { lavaFloor(inputLines()) }
}

private fun lavaFloor(input: List<String>): Any {
    fun energize(grid: List<String>, start: Triple<Int, Int, Direction>): Int {
        val maxX = grid.first().lastIndex
        val maxY = grid.lastIndex

        val energ = mutableSetOf<Triple<Int, Int, Direction>>()
        val paths = mutableListOf(start)

        while (paths.isNotEmpty()) {
            val place = paths.removeFirst()
            if (place in energ) continue
            energ.add(place)

            val c = grid[place.first][place.second]
            val dirs = guide[Pair(c, place.third)] ?: listOf()
            for (dir in dirs) {
                val nextPoint = Triple(place.first + dir.y, place.second + dir.x, dir)
                if (nextPoint.first in 0..maxY && nextPoint.second in 0..maxX) paths.add(nextPoint)
            }
        }
        return energ.map { Pair(it.first, it.second) }.toSet().size
    }

    val part1 = energize(input, Triple(0, 0, RIGHT))

    var part2 = 0
    for (y in input.indices) {
        part2 = max(part2, energize(input, Triple(y, 0, RIGHT)))
        part2 = max(part2, energize(input, Triple(y, (input.first().lastIndex), LEFT)))
    }
    for (x in input.first().indices) {
        part2 = max(part2, energize(input, Triple(0, x, UP)))
        part2 = max(part2, energize(input, Triple(input.lastIndex, x, DOWN)))
    }

    return part1 to part2
}

private val guide = mapOf(
    Pair('.', DOWN) to listOf(DOWN),
    Pair('.', UP) to listOf(UP),
    Pair('.', RIGHT) to listOf(RIGHT),
    Pair('.', LEFT) to listOf(LEFT),

    Pair('-', DOWN) to listOf(RIGHT, LEFT),
    Pair('-', UP) to listOf(RIGHT, LEFT),
    Pair('-', RIGHT) to listOf(RIGHT),
    Pair('-', LEFT) to listOf(LEFT),

    Pair('|', DOWN) to listOf(DOWN),
    Pair('|', UP) to listOf(UP),
    Pair('|', RIGHT) to listOf(UP, DOWN),
    Pair('|', LEFT) to listOf(UP, DOWN),

    Pair('/', DOWN) to listOf(RIGHT),
    Pair('/', UP) to listOf(LEFT),
    Pair('/', RIGHT) to listOf(DOWN),
    Pair('/', LEFT) to listOf(UP),

    Pair('\\', DOWN) to listOf(LEFT),
    Pair('\\', UP) to listOf(RIGHT),
    Pair('\\', RIGHT) to listOf(UP),
    Pair('\\', LEFT) to listOf(DOWN),
)
