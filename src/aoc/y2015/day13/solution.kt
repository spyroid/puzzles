package aoc.y2015.day13

import gears.permutations
import gears.puzzle

private fun main() {
    puzzle {
        part1(linesFrom("test.txt"))
    }
    puzzle {
        part1(linesFrom("input.txt"))
    }
    puzzle {
        part2()
    }
}

private const val me = "Andrei"
private val names = mutableSetOf<String>()
private val map = mutableMapOf<String, Int>()

private fun happiness() = permutations(names.toList())
    .map { it.plus(it.first()) }
    .map { path -> path.windowed(2) { (a, b) -> map["$a$b"]!! + map["$b$a"]!! }.sum() }
    .maxBy { it }

private val re = "(\\w+).*(gain|lose) (\\d+).+ to (\\w+).".toRegex()
private fun part1(lines: List<String>): Int {
    for (line in lines) {
        val (a, s, v, b) = re.find(line)?.groupValues?.drop(1) ?: throw RuntimeException(line)
        names.add(a)
        names.add(b)
        map["$a$b"] = if (s == "lose") -v.toInt() else v.toInt()
    }
    return happiness()
}

private fun part2(): Int {
    for (n in names) {
        map["$n$me"] = 0
        map["$me$n"] = 0
    }
    names.add(me)
    return happiness()
}
