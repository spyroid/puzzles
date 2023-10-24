package aoc.y2018.day25

import gears.findIntNumbers
import gears.puzzle
import kotlin.math.abs

private fun main() {
    puzzle { solution(inputLines()) }
}

private fun solution(input: List<String>): Any {
    val all = ArrayDeque(input.map { it.findIntNumbers().let { p -> Point4E(p[0], p[1], p[2], p[3]) } })
    val constellations = mutableListOf(mutableSetOf(all.removeFirst()))
    while (all.isNotEmpty()) {
        val added = all.toList().count { if (constellations.last().offer(it)) all.remove(it) else false }
        if (added == 0) all.removeFirstOrNull()?.let { constellations.add(mutableSetOf(it)) } ?: break
    }
    return constellations.size
}

private fun MutableSet<Point4E>.offer(p: Point4E) = if (any { it.distance(p) <= 3 }) add(p) else false

private data class Point4E(val a: Int, val b: Int, val c: Int, val d: Int) {
    fun distance(p: Point4E) = abs(a - p.a) + abs(b - p.b) + abs(c - p.c) + abs(d - p.d)
}
