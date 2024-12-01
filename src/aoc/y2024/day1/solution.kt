package aoc.y2024.day1

import gears.puzzle
import kotlin.math.abs

fun main() {
    puzzle {
        historianHysteria(inputLines())
    }
}

private fun historianHysteria(input: List<String>): Any {
    val (a1, a2) = input.map { it.split("\\s+".toRegex()).map(String::toInt) }.map { (a, b) -> a to b }.unzip()
    val p1 = a1.sorted().zip(a2.sorted()).sumOf { (a, b) -> abs(a - b) }
    val map = a2.groupingBy { it }.eachCount()
    val p2 = a1.sumOf { (map[it] ?: 0) * it }

    return p1 to p2
}