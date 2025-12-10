package aoc.y2016.day1

import gears.Direction
import gears.Point
import gears.puzzle

private fun main() {
    puzzle { taxicab(inputLines("input.txt")) }
    puzzle { taxicab(inputLines("input.txt"), true) }
}

private fun taxicab(lines: List<String>, twice: Boolean = false): Int {
    var p = Point.ZERO
    var dir = Direction.UP
    val set = mutableSetOf(p)

    lines.first().split(", ").onEach {
        dir = if (it.first() == 'L') dir.turnCw() else dir.turnCcw()
        repeat(it.drop(1).toInt()) {
            p = p.plus(dir.asPoint())
            if (twice && set.contains(p)) return p.manhattan()
            set.add(p)
        }
    }
    return p.manhattan()
}
