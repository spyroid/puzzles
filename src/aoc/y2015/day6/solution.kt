package aoc.y2015.day6

import gears.puzzle

private fun main() {
    puzzle { part1(inputLines("test.txt")) }
    puzzle { part1(inputLines("input.txt")) }
    puzzle { part2(inputLines("input.txt")) }
}

private fun part1(all: List<String>): Int {
    val data = Array(1000) { Array(1000) { false } }
    all.forEach {
        val (op, xRange, yRange) = parse(it)
        xRange.forEach { x ->
            yRange.forEach { y ->
                data[x][y] = when (op) {
                    "turn on" -> true
                    "turn off" -> false
                    else -> !data[x][y]
                }
            }
        }
    }
    return data.flatten().count { it }
}

private fun part2(all: List<String>): Int {
    val data = Array(1000) { Array(1000) { 0 } }
    all.forEach {
        val (op, xRange, yRange) = parse(it)
        xRange.forEach { x ->
            yRange.forEach { y ->
                data[x][y] = when (op) {
                    "turn on" -> data[x][y] + 1
                    "turn off" -> maxOf(data[x][y] - 1, 0)
                    else -> data[x][y] + 2
                }
            }
        }
    }
    return data.flatten().sum()
}

private val r = "(.+)\\s(\\d+),(\\d+)\\s.*\\s(\\d+),(\\d+)".toRegex()
private fun parse(s: String): Triple<String, IntRange, IntRange> {
    val (op, x1, y1, x2, y2) = r.find(s)?.groupValues?.drop(1) ?: listOf()
    return Triple(op, IntRange(x1.toInt(), x2.toInt()), IntRange(y1.toInt(), y2.toInt()))
}
