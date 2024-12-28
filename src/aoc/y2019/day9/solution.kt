package aoc.y2019.day9

import aoc.y2019.day5.IntComputer
import gears.findLongs
import gears.puzzle

fun main() {
    puzzle { `Sensor Boost`(input().findLongs()) }
}

private fun `Sensor Boost`(program: List<Long>): Any {
    return IntComputer.of(program).run(1) to IntComputer.of(program).run(2)
}