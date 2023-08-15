package aoc.y2016.day4

import gears.puzzle

private fun main() {
    puzzle { obscurity(linesFrom("input.txt")) }
    puzzle { obscurity2(linesFrom("input.txt")) }
}

private fun obscurity2(lines: List<String>): Int {
    return lines.map {
        val s = it.substringBeforeLast("-")
        val id = it.substringAfterLast("-").substringBefore("[").toInt()
        id to s.map { c -> if (c == '-') ' ' else ((c.code - 'a'.code + id) % 26 + 'a'.code).toChar() }.joinToString("")
    }.first { it.second == "northpole object storage" }.first
}

private fun obscurity(lines: List<String>): Int {
    return lines.sumOf {
        val s = it.substringBeforeLast("-")
        val id = it.substringAfterLast("-").substringBefore("[").toInt()
        val checksum = it.substringAfterLast("[").dropLast(1)
        if (hash(s).startsWith(checksum)) id else 0
    }
}

private fun hash(s: String): String {
    return s
        .replace("-", "")
        .groupingBy { it }
        .eachCount()
        .toList()
        .sortedWith(compareByDescending<Pair<Char, Int>> { it.second }.thenBy { it.first })
        .map { it.first }
        .joinToString("")
}
