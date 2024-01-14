package aoc.y2023.day24

import gears.findLongs
import gears.puzzle

private fun main() {
    puzzle("1") { neverTellMeTheOdds(inputLines().toHailstones()) }
    puzzle("2") { neverTellMeTheOdds2(inputLines().toHailstones()) }
}

private fun neverTellMeTheOdds(hailstones: List<Hailstone>): Any {
    val range = 200000000000000.0..400000000000000.0
    return sequence { for (i in 0..<hailstones.lastIndex) for (j in (i + 1)..hailstones.lastIndex) yield(hailstones[i].intersect(hailstones[j])) }
        .filter { it != null && it.x in range && it.y in range }
        .count()
}

private fun neverTellMeTheOdds2(hailstones: List<Hailstone>): Any {
    val range = -500L..500L
    while (true) {
        val hails = hailstones.shuffled().take(4)
        for (dx in range) for (dy in range) {
            val hail0 = hails.first().withVelocityDelta(dx, dy)
            val others = hails.drop(1).mapNotNull { it.withVelocityDelta(dx, dy).intersect(hail0) }
            if (others.size == 3 && others.all { it.x == others.first().x } && others.all { it.y == others.first().y }) {
                range.forEach { dz ->
                    val (z1, z2, z3) = (0..2).map { hails[it + 1].predictZ(others[it].time, dz) }
                    if (z1 == z2 && z2 == z3) return (others.first().x + others.first().y + z1).toLong()
                }
            }
        }
    }
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

    fun predictZ(time: Double, dz: Long): Double = (z + time * (vz + dz))

    fun withVelocityDelta(vx: Long, vy: Long): Hailstone = copy(vx = this.vx + vx, vy = this.vy + vy)
}

private data class Intersection(val x: Double, val y: Double, val time: Double)

private fun List<String>.toHailstones() = map { s -> s.findLongs().let { Hailstone(it[0], it[1], it[2], it[3], it[4], it[5]) } }
