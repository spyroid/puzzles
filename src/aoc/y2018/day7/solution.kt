package aoc.y2018.day7

import gears.puzzle

fun main() {
    puzzle { theSumOfItsParts(inputLines()) }
}

private fun theSumOfItsParts(lines: List<String>): Any {
    var pairs = lines.map { it.split(" ") }.map { it[7].first() to it[1].first() }
    val steps = pairs.flatMap { it.toList() }.toMutableSet()

    val path = mutableListOf<Char>()
    while (steps.isNotEmpty()) {
        val next = steps.filter { s -> pairs.all { it.first != s } }.minOf { it }
        path.add(next).also { steps.remove(next) }
        pairs = pairs.filter { it.second != next }
    }

    val part1 = path.joinToString("")

    val result = mutableListOf<Char>()
    var workers = 0
    var second = 0
    var until = mutableMapOf<Char, Int>()
    pairs = lines.map { it.split(" ") }.map { it[7].first() to it[1].first() }
    val characters = pairs.flatMap { listOf(it.first, it.second) }.toSet().sorted().toList()
    val preReqs = pairs.groupBy { it.second }.map { it.key to it.value.map { it.first }.toSet() }.toMap()

    while (result.size < characters.size) {
        with(until.filter { it.value == second }.keys.sorted()) {
            forEach { until.remove(it) }
            workers -= size
            result += this
        }

        characters.filterNot { result.contains(it) || until.keys.contains(it) }
            .filter { a -> !preReqs.containsKey(a) || preReqs[a]!!.all { b -> result.contains(b) } }
            .sorted()
            .take(5 - workers)
            .also { workers += it.size }
            .forEach { until[it] = second + (it - 'A' + 61) }

        second++
    }

    return part1 to second - 1
}