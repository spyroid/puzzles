package aoc.y2019.day5

import gears.findLongs
import gears.puzzle

fun main() {
    puzzle { sunnyChanceAsteroids(input().findLongs()) }
}

private fun sunnyChanceAsteroids(data: List<Long>): Any {
    return IntComputer.of(data).run(1) to IntComputer.of(data).run(5)
}
