package aoc.y2024.day2

import gears.puzzle
import kotlin.math.abs

fun main() {
    puzzle {
        redNosedReports(inputLines())
    }
}

private fun redNosedReports(input: List<String>): Any {
    val ints = input.map { s -> s.split(" ").map { it.toInt() } }
    fun incOrDec(list: List<Int>) = list.zipWithNext { a, b -> a - b }
        .let { df -> (df.all { it > 0 } || df.all { it < 0 }) && df.all { abs(it) in 1..3 } }

    val p1 = ints.count { incOrDec(it) }
    val extra = ints.count { list ->
        if (!incOrDec(list)) {
            for (idx in list.indices) {
                if (incOrDec(list.mapIndexedNotNull { i, v -> if (idx == i) null else v })) {
                    return@count true
                }
            }
        }
        return@count false
    }
    return p1 to p1 + extra
}