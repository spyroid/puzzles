package aoc.y2019.day6

import gears.puzzle

private fun main() {
    puzzle("1 & 2") { universalOrbitMap(inputLines()) }
}

private fun universalOrbitMap(input: List<String>): Any {
    val map = input.map { it.split(")") }.associate { it.last() to it.first() }

    fun findOrbits(id: String, path: MutableList<String> = mutableListOf()): List<String> {
        return map[id]?.let { path.add(it); findOrbits(it, path) } ?: path
    }

    val part1 = map.keys.sumOf { findOrbits(it).size }

    val (a, b) = findOrbits("YOU") to findOrbits("SAN")
    val part2 = a.first { b.contains(it) }.let { a.indexOf(it) + b.indexOf(it) }

    return part1 to part2
}