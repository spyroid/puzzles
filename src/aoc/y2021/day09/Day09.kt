package aoc.y2021.day09

import gears.readInput
import kotlin.system.measureTimeMillis

fun main() {

    data class Point(val x: Int, val y: Int, val v: Int)

    class Area(input: List<String>) {

        val points = readPoints(input)
        val areaWidth = input.first().length

        fun readPoints(seq: List<String>) = mutableListOf<Point>().apply {
            seq.forEachIndexed { y, line -> line.forEachIndexed { x, v -> this += Point(x, y, v.digitToInt()) } }
        }

        fun at(x: Int, y: Int): Point? {
            if (x < 0 || x >= areaWidth || y < 0) return null
            return points.getOrNull(x + areaWidth * y)
        }

        fun neighbors(x: Int, y: Int) = listOfNotNull(at(x + 1, y), at(x - 1, y), at(x, y + 1), at(x, y - 1))

        fun lowestPoints() = points.filter { p -> neighbors(p.x, p.y).all { it.v > p.v } }

        fun basinsMaxSizes(count: Int) = lowestPoints()
            .map { findBasin(it).size }
            .sortedByDescending { it }
            .take(count)
            .fold(1) { acc, i -> acc * i }

        private fun findBasin(p: Point): MutableSet<Point> {
            fun deeper(p: Point, basin: MutableSet<Point>) {
                basin.add(p)
                neighbors(p.x, p.y).filter { n -> p.v < n.v && n.v < 9 }.forEach { deeper(it, basin) }
            }
            return mutableSetOf<Point>().apply { deeper(p, this) }
        }
    }

    fun part1(input: List<String>) = Area(input).lowestPoints().sumOf { it.v + 1 }

    fun part2(input: List<String>) = Area(input).basinsMaxSizes(3)

    /* init */

    val testData = readInput("day09/test")
    val inputData = readInput("day09/input")

    var res1 = part1(testData)
    check(res1 == 15) { "Expected 15 but got $res1" }

    var time = measureTimeMillis { res1 = part1(inputData) }
    println("Part1: $res1 in $time ms")

    res1 = part2(testData)
    check(res1 == 1134) { "Expected 1134 but got $res1" }

    time = measureTimeMillis { res1 = part2(inputData) }
    println("Part2: $res1 in $time ms")
}

