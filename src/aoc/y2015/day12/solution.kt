package aoc.y2015.day12

import gears.puzzle

private fun main() {
    puzzle {
        part1(inputLines("input.txt").first())
    }
    puzzle {
        part2(inputLines("input.txt").first())
    }
}

private fun part1(json: String) = json.replace("[^0-9-]".toRegex(), " ")
    .split("\\s+".toRegex())
    .filter { it.isNotBlank() }
    .sumOf { it.toInt() }

private val re = "\\{[^{}]*\\}".toRegex()
private fun part2(json: String): String {
    return if (re.containsMatchIn(json)) {
        part2(re.replace(json) { m -> if (m.value.contains(":\"red")) "0" else part1(m.value).toString() })
    } else {
        part1(json).toString()
    }
}

