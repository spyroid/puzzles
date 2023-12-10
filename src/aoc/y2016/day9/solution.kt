package aoc.y2016.day9

import gears.puzzle
import kotlin.math.min

private fun main() {
    puzzle { cyberspace("X(8x2)(3x3)ABCY") }
    puzzle { cyberspace(inputLines("input.txt").first()) }
    puzzle { cyberspace2("X(8x2)(3x3)ABCY") }
    puzzle { cyberspace2(inputLines("input.txt").first()) }
}

private fun cyberspace2(line: String): Long {
    var i = -1
    var len = 0L
    val multi = IntArray(line.length) { 1 }

    while (++i < line.length) {
        val mul = multi[i]
        if (line[i] == '(') {
            val m = line.substring(i + 1, line.indexOf(")", i))
            val (a, b) = m.split("x").map { it.toInt() }
            val pos = i + m.length + 1
            for (j in (pos + 1)..min(line.lastIndex, a + pos)) multi[j] = mul * b
            i += m.length + 1
        } else len += mul
    }
    return len
}

private fun cyberspace(line: String): Int {
    var i = -1
    var len = 0
    while (++i < line.length) {
        if (line[i] == '(') {
            val m = line.substring(i + 1, line.indexOf(")", i))
            val (a, b) = m.split("x").map { it.toInt() }
            len += a * b
            i += a + m.length + 1
        } else len++
    }
    return len
}
