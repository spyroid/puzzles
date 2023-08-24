package aoc.y2021.day11

import gears.puzzle

fun main() {
    puzzle { part1(linesFrom("input.txt")) }
    puzzle { part2(linesFrom("input.txt")) }
}

private data class Point(val x: Int, val y: Int, var v: Int) {
    fun inc(): Int {
        if (v == 9) v = 0 else v++
        return v
    }
}

private class Area(input: List<String>) {

    val points = readPoints(input)
    val areaWidth = input.first().length
    val areaHeight = input.size
    var totalFlashes = 0

    fun readPoints(seq: List<String>) = mutableListOf<Point>().apply {
        seq.forEachIndexed { y, line -> line.forEachIndexed { x, v -> this += Point(x, y, v.digitToInt()) } }
    }

    fun at(x: Int, y: Int): Point? {
        if (x < 0 || x >= areaWidth || y < 0) return null
        return points.getOrNull(x + areaWidth * y)
    }

    fun neighbors(x: Int, y: Int) = listOfNotNull(
        at(x - 1, y - 1), at(x, y - 1), at(x + 1, y - 1),
        at(x - 1, y), at(x + 1, y),
        at(x - 1, y + 1), at(x, y + 1), at(x + 1, y + 1),
    )

    override fun toString(): String {
        return buildString {
            for (y in 0 until areaHeight) {
                for (x in 0 until areaHeight) append(at(x, y)?.v).append(" ")
                append("\n")
            }
        }
    }

    fun oneStep() {
        var zeroes = mutableSetOf<Point>()
        val excludes = mutableSetOf<Point>()

        points.filter { it.inc() == 0 }.forEach {
            excludes.add(it)
            zeroes.add(it)
            totalFlashes++
        }

        while (zeroes.isNotEmpty()) {
            zeroes = mutableSetOf<Point>().apply {
                zeroes.forEach { z ->
                    neighbors(z.x, z.y)
                        .filterNot { it.v == 0 && it in excludes }
                        .filter { it.inc() == 0 }
                        .forEach {
                            excludes.add(it)
                            add(it)
                            totalFlashes++
                        }
                }
            }
        }
    }

    fun allFlashing() = points.sumOf { it.v } == 0
}

private fun part1(input: List<String>): Int {
    val area = Area(input)
    return repeat(100) { area.oneStep() }
        .let { area.totalFlashes }
}

private fun part2(input: List<String>): Int {
    val area = Area(input)
    return generateSequence(1) { it + 1 }
        .onEach { area.oneStep() }
        .first { area.allFlashing() }
}
