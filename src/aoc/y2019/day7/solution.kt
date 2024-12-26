package aoc.y2019.day7

import aoc.y2019.day5.IntComputer
import gears.findInts
import gears.puzzle

fun main() {
    puzzle { `Amplification Circuit`(input().findInts()) }
}

private fun `Amplification Circuit`(input: List<Int>): Any {

    val phases = listOf(4, 3, 2, 1, 0)
    var io = 0

    phases.forEach { phase ->
        val computer = IntComputer.of(input, listOf(phase, io))
        while (computer.isNotTerminated()) computer.run()
        io = computer.output
    }

    return io
}
