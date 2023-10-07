package aoc.y2017.day14

import aoc.y2017.day10.knot2
import gears.Grid
import gears.puzzle
import java.math.BigInteger

private fun main() {
    puzzle { defragmentator("ljoxqyyw") }
}

private fun defragmentator(input: String): Any {
    val disk = Grid.of(128, 128, 0)
    var part1 = 0
    for (y in 0..127) {
        BigInteger(knot2("$input-$y"), 16)
            .toString(2)
            .padStart(128, '0')
            .forEachIndexed { x, c -> disk[x, y] = c.digitToInt().also { part1 += it } }
    }

    fun markGroup(x: Int, y: Int) {
        if (disk[x, y] == 1) {
            disk[x, y] = 0
            disk.pointsAround(x, y).forEach { markGroup(it.x, it.y) }
        }
    }

    var part2 = 0
    disk.pointsOf { x, y, v -> if (v == 1) markGroup(x, y).also { part2++ }; false }
    return part1 to part2
}
