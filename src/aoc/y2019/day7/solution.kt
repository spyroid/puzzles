package aoc.y2019.day7

import aoc.y2019.day5.IntComputer
import aoc.y2019.day5.IntComputer.State.TERMINATED
import gears.findInts
import gears.permutations
import gears.puzzle

fun main() {
    puzzle { `Amplification Circuit`(input().findInts()) }
}

private fun `Amplification Circuit`(program: List<Int>): Any {

    val part1 = permutations(listOf(0, 1, 2, 3, 4)).maxOf {
        it.fold(0) { io, phase -> IntComputer.of(program).run(listOf(phase, io)) }
    }

    fun testRun(phases: List<Int>): Int {
        val amps = List(5) { IntComputer.of(program) }
        var (ap, io) = 0 to 0
        while (amps.last().state != TERMINATED) {
            amps.forEach { amp ->
                val inputs = if (ap > phases.lastIndex) listOf(io) else listOf(phases[ap++], io)
                io = amp.run(inputs)
            }
        }
        return io
    }

    val part2 = permutations(listOf(5, 6, 7, 8, 9)).maxOf { testRun(it) }

    return part1 to part2
}
