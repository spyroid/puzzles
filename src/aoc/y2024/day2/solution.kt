package aoc.y2024.day2

import gears.puzzle

fun main() {
    puzzle {
        redNosedReports(inputLines())
    }
}

private fun redNosedReports(input: List<String>): Any {
    fun incOrDec(list: List<Int>) = list.zipWithNext { a, b -> a - b }
        .let { df -> df.all { it in 1..3 } || df.all { it in -3..-1 } }

    val (a, b) = input.map { s -> s.split(" ").map { it.toInt() } }.map { list ->
        incOrDec(list) to list.indices.any { incOrDec(list.filterIndexed { i, _ -> i != it }) }
    }.unzip()
    return a.count { it } to b.count { it }
}