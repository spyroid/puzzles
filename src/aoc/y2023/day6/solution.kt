package aoc.y2023.day6

import gears.findLongNumbers
import gears.findNumbers
import gears.puzzle
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToLong
import kotlin.math.sqrt

private fun main() {
    puzzle("1") { wait1(inputLines()) }
    puzzle("2") { wait2(inputLines()) }
}

private fun wait1(input: List<String>): Any {
    val pairs = input.first().findLongNumbers()
        .zip(input.last().findLongNumbers())
    return race(pairs)
}

private fun wait2(input: List<String>): Any {
    val a = input.first().findNumbers().joinToString("").toLong()
    val b = input.last().findNumbers().joinToString("").toLong()
    return race(listOf(Pair(a, b)))
}

private fun race(pairs: List<Pair<Long, Long>>) = pairs.fold(1L) { acc, p ->
    val dx = sqrt(p.first * p.first - 4.0 * p.second)
    val min = floor((p.first - dx) / 2 + 1)
    val max = ceil((p.first + dx) / 2 - 1)
    (acc * (max - min + 1)).roundToLong()
}
