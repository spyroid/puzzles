package aoc.y2017.day6

import gears.getCircular
import gears.puzzle
import gears.setCircular

private fun main() {
    puzzle { malloc(linesFrom("input.txt").first().split("\\s+".toRegex()).map { it.toInt() }) }
}

private fun malloc(input: List<Int>, part2: Boolean = false): Pair<Int, Int> {
    val mem = input.toMutableList()
    val map = mutableMapOf<Int, MutableList<Int>>()
    var steps = 1
    while (true) {
        val max = mem.max()
        var idx = mem.indexOf(max)
        mem.setCircular(idx, 0)
        repeat(max) { mem.setCircular(++idx, mem.getCircular(idx) + 1) }

        map.computeIfAbsent(mem.hashCode()) { mutableListOf() }.run {
            add(steps++)
            if (size > 1) return last() to (last() - first())
        }
    }
}

