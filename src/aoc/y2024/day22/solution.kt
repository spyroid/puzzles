package aoc.y2024.day22

import gears.Point
import gears.puzzle

fun main() {
    puzzle { xxx(inputLines()) }
}

private fun xxx(input: List<String>): Any {


    val p = Point(5, 5)

//    val grid = Grid.of(10, 10, '.').apply {
//        p.allManhattan(2).forEach { set(it, fullBlock) }
//    }

    val ww = p.allInBounds(6, 6)

    return input.lastIndex
}

