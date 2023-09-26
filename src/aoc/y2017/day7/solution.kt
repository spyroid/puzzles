package aoc.y2017.day7

import gears.puzzle

private fun main() {
    puzzle { circus(linesFrom("input.txt")) }
}

private fun circus(input: List<String>, part2: Boolean = false): String {

    val all = input.map { line ->
        val (name, a) = line.split(" (")
        val (age, b) = a.split(")")
        val others = if (b.contains("->")) b.split(" -> ").last().split(", ") else listOf()
        Triple(name, age, others.toSet())
    }

    val names = all.map { it.first }.toMutableSet().also {
        all.forEach { e -> it.removeAll(e.third) }
    }
    return names.first()
}

