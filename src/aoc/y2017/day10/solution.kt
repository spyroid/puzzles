package aoc.y2017.day10

import gears.puzzle
import gears.reverseSubList

private fun main() {
    puzzle { knot(inputLines().first()) }
    puzzle { knot2(inputLines().first()) }
}

@OptIn(ExperimentalStdlibApi::class)
fun knot2(str: String): String {
    val offsets = str.map { it.code }.plus(listOf(17, 31, 73, 47, 23))
    return rotated(offsets, 64)
        .windowed(16, 16)
        .map { it.fold(0) { acc, v -> acc xor v } }
        .joinToString("") { it.toByte().toHexString(HexFormat.Default) }
}

private fun rotated(offsets: List<Int>, times: Int): MutableList<Int> {
    val list = IntArray(256) { it }.toMutableList()
    var i = 0
    var skip = 0

    repeat(times) {
        offsets.forEach { o -> list.reverseSubList(i, i + o - 1).also { i += o + skip++ } }
    }
    return list
}

private fun knot(str: String): Any {
    val offsets = str.split(",\\s*".toRegex()).map { it.toInt() }
    return rotated(offsets, 1).let { it[0] * it[1] }
}
