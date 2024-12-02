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

    val (a, b) = ints.map { list ->
        incOrDec(list) to list.indices.any {
            incOrDec(list.mapIndexedNotNull { i, v -> if (it == i) null else v })
        }
    }.unzip()
    return a.count { it } to b.count { it }
}