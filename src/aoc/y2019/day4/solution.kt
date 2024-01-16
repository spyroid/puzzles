package aoc.y2019.day4

import gears.puzzle

private fun main() {
    puzzle("1 & 2") { secureContainer("245182-790572") }
}

private fun secureContainer(input: String) = input
    .split("-").map { it.toInt() }
    .let { (a, b) -> (a..b).asSequence().map { it.toString() } }
    .filterNot { it.zipWithNext().any { (a, b) -> a > b } }
    .map { s ->
        val groups = s.groupingBy { it }.eachCount().filterValues { it > 1 }
        groups.isNotEmpty().compareTo(false) to s.any { groups[it] == 2 }.compareTo(false)
    }.fold(0 to 0) { acc, pair -> acc.first + pair.first to acc.second + pair.second }
