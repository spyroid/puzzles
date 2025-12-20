package aoc.y2025.day10

import gears.combinations
import gears.puzzle
import gears.symmetricDifference

fun main() {
    puzzle {
        factory(inputLines())
    }
}

private fun factory(input: List<String>): Any {

    val set = setOf(1, 4)
    val out = set.symmetricDifference(setOf(4, 3))

    val list = listOf(1, 2, 3, 4)

    list.combinations(2).onEach { println(it) }.count()


    return out
}
