package aoc.y2015.day19

import gears.puzzle

private fun main() {
    puzzle { medicine1(inputLines("input.txt").asPair()) }
    puzzle { medicine2(inputLines("input.txt")) }
}

private fun medicine2(input: List<String>): Int {
    val map = input.takeWhile { it.isNotEmpty() }.map { it.split(" => ") }.associate { it.last() to it.first() }

    fun backtrace(formula: String, map: Map<String, String>): Int {
        var count = 0
        var oldFormula = ""
        val keys = map.keys.shuffled()
        var s = formula

        while (oldFormula != s) {
            oldFormula = s
            for (key in keys) {
                while (s.contains(key)) s = s.replaceFirst(key, map[key]!!).also { count++ }
            }
        }
        return if (s == "e") count else 0
    }
    return generateSequence { backtrace(input.last(), map) }.first { it != 0 }
}

private fun medicine1(input: Pair<Map<String, List<String>>, List<String>>): Int {
    return buildSet {
        input.second.onEachIndexed { i, p ->
            for (v in input.first.getOrDefault(p, listOf())) {
                input.second.toMutableList().apply { set(i, v) }.joinToString("").also { add(it) }
            }
        }
    }.size
}

private fun List<String>.asPair(): Pair<Map<String, List<String>>, List<String>> {
    val map = this.takeWhile { it.isNotEmpty() }.map { it.split(" => ") }.map { it.first() to it.last() }.groupBy({ it.first }, { it.second })

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
