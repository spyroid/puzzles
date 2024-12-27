package aoc.y2019.day9

import aoc.y2019.day5.IntComputer
import gears.findLongs
import gears.puzzle

fun main() {
    puzzle{ `Sensor Boost`(input().findLongs()) }
}

private fun `Sensor Boost`(input: List<Long>): Any {

    val part1 = IntComputer.of(input)
    part1.run(input)
    return part1.output
}