package aoc.y2015.day6

import gears.puzzle

private fun main() {
    puzzle { part1(linesFrom("test.txt")) }
    puzzle { part1(linesFrom("input.txt")) }
    puzzle { part2(linesFrom("input.txt")) }
}

private fun part1(all: List<String>): Int {
    val data = Array(1000) { Array(1000) { false } }
    all.forEach {
        val (op, xRange, yRange) = parse(it)
        when (op) {
            "turn on" -> xRange.forEach { x -> yRange.forEach { y -> data[x][y] = true } }
            "turn off" -> xRange.forEach { x -> yRange.forEach { y -> data[x][y] = false } }
            else -> xRange.forEach { x -> yRange.forEach { y -> data[x][y] = !data[x][y] } }
        }
    }
    return data.flatten().count { it }
}

private fun part2(all: List<String>): Int {
    val data = Array(1000) { Array(1000) { 0 } }
    all.forEach {
        val (op, xRange, yRange) = parse(it)
        val inc = when (op) {
            "turn on" -> 1
            "turn off" -> -1
            else -> 2
        }
        xRange.forEach { x -> yRange.forEach { y -> data[x][y] = maxOf(data[x][y] + inc, 0) } }
    }
    return data.flatten().sum()
}

val r = "(.+)\\s(\\d+),(\\d+)\\s.*\\s(\\d+),(\\d+)".toRegex()
private fun parse(s: String): Triple<String, IntRange, IntRange> {
    val (op, x1, y1, x2, y2) = r.find(s)?.groupValues?.drop(1) ?: listOf()
    return Triple(op, IntRange(x1.toInt(), x2.toInt()), IntRange(y1.toInt(), y2.toInt()))
}
