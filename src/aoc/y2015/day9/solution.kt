package aoc.y2015.day9

import gears.permutations
import gears.puzzle

private fun main() {
    puzzle { part1(inputLines("test.txt")).min() }
    puzzle { part1(inputLines("input.txt")).min() }
    puzzle { part1(inputLines("input.txt")).max() }
}

private val r = "(\\w+) to (\\w+) = (\\d+)".toRegex()

private fun part1(lines: List<String>): List<Int> {
    val dist = mutableMapOf<String, Int>()
    val cities = mutableSetOf<String>()
    for (line in lines) {
        val (p1, p2, d) = r.find(line)?.groupValues?.drop(1) ?: throw RuntimeException()
        dist["$p1$p2"] = d.toInt()
        dist["$p2$p1"] = d.toInt()
        cities.add(p1)
        cities.add(p2)
    }
    return permutations(cities.toList()).map { route -> route.zipWithNext { a, b -> dist["$a$b"] ?: 0 }.sumOf { it } }
}

