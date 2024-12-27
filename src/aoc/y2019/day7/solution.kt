package aoc.y2019.day7

import aoc.y2019.day5.IntComputer
import gears.findInts
import gears.permutations
import gears.puzzle

fun main() {
    puzzle { `Amplification Circuit`(input().findInts()) }
}

private fun `Amplification Circuit`(input: List<Int>): Any {

    val part1 = permutations(listOf(0, 1, 2, 3, 4)).maxOf {
        it.fold(0) { io, phase -> IntComputer.of(input, listOf(phase, io)).run() }
    }

    return part1
}
