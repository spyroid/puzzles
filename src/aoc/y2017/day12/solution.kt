package aoc.y2017.day12

import gears.puzzle

private fun main() {
    puzzle { plumber(inputLines()) }
}

private fun plumber(input: List<String>): Any {
    val all = input.map { s -> "\\d+".toRegex().findAll(s).map { it.value.toInt() } }.associate { it.first() to it.drop(1).toSet() }

    fun find(id: Int, set: MutableSet<Int>): Set<Int> {
        set.add(id)
        val items = all[id]?.filterNot { it in set } ?: listOf()
        for (e in items) find(e, set)
        return set
    }

    val visited = mutableSetOf<Int>()
    val groups = mutableMapOf<Int, Int>()

    while (all.size > visited.size) {
        val subVisited = mutableSetOf<Int>()
        val group = all.keys.filterNot { visited.contains(it) }.minOf { it }

        find(group, subVisited)

        groups[group] = subVisited.size
        visited += subVisited
    }
    return groups[0] to groups.size
}

