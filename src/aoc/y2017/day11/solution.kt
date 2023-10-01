package aoc.y2017.day11

import gears.puzzle
import kotlin.math.abs

private fun main() {
    puzzle { hexed(inputLines().first().split(",")) }
}

private fun hexed(input: List<String>): Any {
    var (s, q, r) = Triple(0, 0, 0)
    fun dist() = (abs(s) + abs(q) + abs(r)) / 2
    val max = input.maxOf {
        when (it) {
            "n" -> s = s.inc().also { r = r.dec() }
            "s" -> s = s.dec().also { r = r.inc() }
            "ne" -> q = q.inc().also { r = r.dec() }
            "sw" -> q = q.dec().also { r = r.inc() }
            "nw" -> s = s.inc().also { q = q.dec() }
            "se" -> s = s.dec().also { q = q.inc() }
        }
        dist()
    }
    return dist() to max
}
