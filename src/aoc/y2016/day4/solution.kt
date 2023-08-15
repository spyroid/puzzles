package aoc.y2016.day4

import gears.puzzle

private fun main() {
    puzzle { obscurity(linesFrom("input.txt")) }
    puzzle { obscurity2(linesFrom("input.txt")) }
}

private fun obscurity2(lines: List<String>): Int {
    return lines.map { line ->
        val (s, id) = parse(line)
        id to s.map { if (it == '-') ' ' else ((it.code - 'a'.code + id) % 26 + 'a'.code).toChar() }.joinToString("")
    }.first { it.second == "northpole object storage" }.first
}

private fun obscurity(lines: List<String>): Int {
    return lines.sumOf {
        val (s, id) = parse(it)
        val checksum = it.substringAfterLast("[").dropLast(1)
        if (hash(s).startsWith(checksum)) id else 0
    }
}

private fun parse(s: String) = Pair(s.substringBeforeLast("-"), s.substringAfterLast("-").substringBefore("[").toInt())

private fun hash(s: String) = s.replace("-", "").groupingBy { it }.eachCount().toList()
    .sortedWith(compareByDescending<Pair<Char, Int>> { it.second }.thenBy { it.first })
    .map { it.first }.joinToString("")
