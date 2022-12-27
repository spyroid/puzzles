package aoc.y2022.day25

import gears.puzzle
import kotlin.math.pow

private class FullOfHotAir {
    fun part1(lines: List<String>): String = encode(lines.map(::decode).sum())

    private fun encode(value: Long): String {
        if (value == 0L) return ""
        return when (value % 5) {
            0L, 1L, 2L -> return encode(value / 5) + (value % 5)
            3L -> encode(1 + value / 5) + "="
            4L -> encode(1 + value / 5) + "-"
            else -> ""
        }
    }

    val snafu = mapOf('=' to -2, '-' to -1, '0' to 0, '1' to 1, '2' to 2)

    private fun decode(exp: String): Long =
        exp.reversed().mapIndexed { i, c -> 5.0.pow(i) * (snafu[c] ?: 0) }.sum().toLong()
}

fun main() {
    puzzle("t1") { FullOfHotAir().part1(linesFrom("test.txt")) }
    puzzle("1") { FullOfHotAir().part1(linesFrom("input.txt")) }
}
