package aoc.y2023.day24

import gears.findLongs
import gears.puzzle

private fun main() {
    puzzle("1") { neverTellMeTheOdds(inputLines()) }
}

private fun neverTellMeTheOdds(input: List<String>): Any {
    val hailstones = input.map { s -> s.findLongs().let { Hailstone(it[0], it[1], it[2], it[3], it[4], it[5]) } }
//    val range = 7.0..27.0
    val range = 200000000000000.0..400000000000000.0
    return sequence { for (i in 0..<hailstones.lastIndex) for (j in (i + 1)..hailstones.lastIndex) yield(hailstones[i].intersect(hailstones[j])) }
        .filter { it != null && it.x in range && it.y in range }
        .count()
}

private data class Hailstone(val x: Long, val y: Long, val z: Long, val vx: Long, val vy: Long, val vz: Long) {
    private val slope = if (vx == 0L) null else vy / vx.toDouble()
    fun intersect(other: Hailstone): Intersection? {
        if (slope == other.slope || slope == null || other.slope == null) return null
        val c = y - slope * x
        val otherC = other.y - other.slope * other.x

        val xx = (otherC - c) / (slope - other.slope)
        val t1 = (xx - x) / vx
        val t2 = (xx - other.x) / other.vx

        if (t1 < 0 || t2 < 0) return null

        val yy = slope * (xx - x) + y
        return Intersection(xx, yy, t1)
    }
}

private data class Intersection(val x: Double, val y: Double, val time: Double)
