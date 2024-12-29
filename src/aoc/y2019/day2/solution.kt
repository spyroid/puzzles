package aoc.y2019.day2

import aoc.y2019.day5.IntComputer
import gears.findLongs
import gears.puzzle

fun main() {
    puzzle { `1202 Program Alarm`(input().findLongs()) }
}

private fun `1202 Program Alarm`(program: List<Long>): Any? {
    fun testRun(a: Long, b: Long) = IntComputer.of(program).apply { memory.set(1, a); memory.set(2, b) }.let { it.run(); it.memory.getValue(0) }

    val part1 = testRun(12, 2)

    var part2 = 0L
    for (a in 0L..99) for (b in 0L..99) {
        if (testRun(a, b) == 19690720L) {
            part2 = a * 100 + b; break
        }
    }

    return part1 to part2
}
