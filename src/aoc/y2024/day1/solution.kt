package aoc.y2024.day1

import gears.puzzle
import kotlin.math.abs

fun main() {
    puzzle {
        HistorianHysteria(inputLines())
    }
}

private fun HistorianHysteria(input: List<String>): Any {
    val a1 = mutableListOf<Int>()
    val a2 = mutableListOf<Int>()
    input.map { it.split("\\s+".toRegex()).map(String::toInt) }.forEach { (a, b) -> a1.add(a); a2.add(b) }
    val p1 = a1.sorted().zip(a2.sorted()).sumOf { (a, b) -> abs(a - b) }
    val map = a2.groupingBy { it }.eachCount()
    val p2 = a1.sumOf { map.getOrElse(it) { 0 } * it }

    return p1 to p2
}