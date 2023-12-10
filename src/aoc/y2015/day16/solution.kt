package aoc.y2015.day16

import gears.puzzle

private fun main() {
    puzzle { sue1(inputLines("input.txt")) }
    puzzle { sue2(inputLines("input.txt")) }
}

private fun sue2(lines: List<String>): Int {
    return lines.asSequence()
        .map { parse(it) }
        .filter { it.getOrDefault("children", 3) == 3 }
        .filter { it.getOrDefault("cats", 7) >= 7 }
        .filter { it.getOrDefault("samoyeds", 2) == 2 }
        .filter { it.getOrDefault("pomeranians", 3) <= 3 }
        .filter { it.getOrDefault("akitas", 0) == 0 }
        .filter { it.getOrDefault("vizslas", 0) == 0 }
        .filter { it.getOrDefault("goldfish", 5) <= 5 }
        .filter { it.getOrDefault("trees", 3) >= 3 }
        .filter { it.getOrDefault("cars", 2) == 2 }
        .filter { it.getOrDefault("perfumes", 1) == 1 }
        .first()["sue"] ?: -1
}

private fun sue1(lines: List<String>): Int {
    return lines.asSequence()
        .map { parse(it) }
        .filter { it.getOrDefault("children", 3) == 3 }
        .filter { it.getOrDefault("cats", 7) == 7 }
        .filter { it.getOrDefault("samoyeds", 2) == 2 }
        .filter { it.getOrDefault("pomeranians", 3) == 3 }
        .filter { it.getOrDefault("akitas", 0) == 0 }
        .filter { it.getOrDefault("vizslas", 0) == 0 }
        .filter { it.getOrDefault("goldfish", 5) == 5 }
        .filter { it.getOrDefault("trees", 3) == 3 }
        .filter { it.getOrDefault("cars", 2) == 2 }
        .filter { it.getOrDefault("perfumes", 1) == 1 }
        .first()["sue"] ?: -1
}

private fun parse(s: String): Map<String, Int> {
    return s.substringAfter(": ").split(", ")
        .map { it.split(": ") }
        .associate { it.first() to it.last().toInt() }
        .plus("sue" to s.substringBefore(":").drop(4).toInt())
}
