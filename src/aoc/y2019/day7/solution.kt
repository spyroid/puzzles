package aoc.y2019.day7

import aoc.y2019.day5.IntComputer
import aoc.y2019.day5.IntComputer.State.TERMINATED
import gears.findLongs
import gears.permutations
import gears.puzzle
import gears.safeGet

fun main() {
    puzzle { `Amplification Circuit`(input().findLongs()) }
}

private fun `Amplification Circuit`(program: List<Long>): Any {
    fun testRun(phases: List<Int>): Long {
        return generateSequence(Triple(0, 0L, List(5) { IntComputer.of(program) })) { (ampNo, io, amps) ->
            val inputs = if (ampNo in amps.indices) longArrayOf(phases[ampNo].toLong(), io) else longArrayOf(io)
            Triple(ampNo + 1, amps.safeGet(ampNo).run(*inputs), amps)
        }.first { (_, _, amps) -> amps.last().state == TERMINATED }.second
    }

    val part1 = permutations(listOf(0, 1, 2, 3, 4)).maxOf { testRun(it) }
    val part2 = permutations(listOf(5, 6, 7, 8, 9)).maxOf { testRun(it) }
    return part1 to part2
}
