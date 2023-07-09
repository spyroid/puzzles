package aoc.y2020.day07

import gears.puzzle

private fun main() {
    puzzle { part1(linesFrom("test.txt")) }
    puzzle { part1(linesFrom("input.txt")) }
    puzzle { part2(linesFrom("test.txt")) }
    puzzle { part2(linesFrom("input.txt")) }

}

private fun part1(lines: List<String>): Int {
    val all = lines.map { parse(it) }
    var set1 = setOf("shiny gold")
    val set2 = mutableSetOf<String>()

    while (set1.isNotEmpty()) {
        set1 = all.mapNotNull { bag ->
            set1.forEach { if (bag.other.containsKey(it)) return@mapNotNull bag.id }
            null
        }.toSet().also { set2.addAll(it) }
    }
    return set2.size
}

private fun part2(lines: List<String>): Int {
    val all = lines.map { parse(it) }.associate { it.id to it.other }
    fun deep(id: String): Int = all[id]?.entries?.sumOf { it.value + it.value * deep(it.key) } ?: 0
    return deep("shiny gold")
}

private fun parse(it: String): Bag {
    val a = it.split(" bags contain ")
    val bag = Bag(a.first(), mutableMapOf())
    if (a.last().contains("no other")) return bag
    a.last().split(", ")
        .forEach { bag.other[it.drop(2).substringBefore(" bag")] = it.take(1).toInt() }
    return bag
}

private data class Bag(val id: String, val other: MutableMap<String, Int>)
