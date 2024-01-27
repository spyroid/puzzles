package aoc.y2019.day8

import gears.puzzle

private fun main() {
    puzzle("1 & 2") { spaceImageFormat(input()) }
}

private fun spaceImageFormat(input: String): Any {
    val (w, h, iSize) = Triple(25, 6, 25 * 6)
    val part1 = input.windowed(iSize, iSize).map { s -> s.count { it == '0' } to s }.minBy { it.first }.let { p ->
        p.second.count { it == '1' } * p.second.count { it == '2' }
    }

    fun layer(idx: Int) = generateSequence(idx) { it + iSize }.takeWhile { it in input.indices }.map { input[it] }.joinToString("")

    (0..<iSize).map { layer(it) }
        .map { s -> s.first { it != '2' }.let { if (it == '1') it else ' ' } }
        .windowed(w, w)
        .onEach { println(it.joinToString("")) }

    return part1
}