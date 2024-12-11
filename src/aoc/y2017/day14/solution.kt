package aoc.y2017.day14

import aoc.y2017.day10.knot2
import gears.Grid
import gears.Point
import gears.puzzle
import java.math.BigInteger

private fun main() {
    puzzle { defragmentator("ljoxqyyw") }
}

private fun defragmentator(input: String): Any {
    val disk = Grid.of(128, 128, 0)
    val part1 = (0..127).sumOf { y ->
        BigInteger(knot2("$input-$y"), 16)
            .toString(2)
            .padStart(128, '0')
            .mapIndexed { x, c -> disk[x, y] = c.digitToInt(); c.digitToInt() }.sum()
    }

    fun markGroup(point: Point) {
        if (disk[point] == 1) {
            disk[point] = 0
            disk.pointsAround4(point).forEach { markGroup(it) }
        }
    }

    var part2 = 0
    disk.allPoints().forEach { if (disk[it] == 1) markGroup(it).also { part2++ } }
    return part1 to part2
}
