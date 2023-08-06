package aoc.y2015.day19

import gears.puzzle

private fun main() {
    puzzle { medicine(linesFrom("input.txt").asPair()) }
}

private fun medicine(input: Pair<Map<String, List<String>>, List<String>>): Int {
    return buildSet {
        input.second.onEachIndexed { i, p ->
            for (v in input.first.getOrDefault(p, listOf())) {
                input.second.toMutableList().apply { set(i, v) }.joinToString("").also { add(it) }
            }
        }
    }.size
}

private fun List<String>.asPair(): Pair<Map<String, List<String>>, List<String>> {
    val map = this.takeWhile { it.isNotEmpty() }
        .map { it.split(" => ") }
        .map { it.first() to it.last() }
        .groupBy({ it.first }, { it.second })

    var formula = last()
    val parts = buildList {
        while (formula.isNotEmpty()) {
            val k = map.keys.firstOrNull { formula.startsWith(it) } ?: formula.first().toString()
            add(k)
            formula = formula.drop(k.length)
        }
    }
    return Pair(map, parts)
}

