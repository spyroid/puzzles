package aoc.y2019.day8

import gears.puzzle

private fun main() {
    puzzle("1 & 2") { spaceImageFormat(input()) }
}

private fun spaceImageFormat(input: String): Any {
    val (w, iSize) = 25 to 25 * 6
    val part1 = input.windowed(iSize, iSize).map { s -> s.count { it == '0' } to s }.minBy { it.first }.let { p ->
        p.second.count { it == '1' } * p.second.count { it == '2' }
    }

    (0..<iSize).map { idx ->
        generateSequence(idx) { it + iSize }.take(input.length / iSize).map { input[it] }.joinToString("")
    }
        .map { s -> s.first { it != '2' }.let { if (it == '1') it else ' ' } }
        .windowed(w, w)
        .onEach { println(it.joinToString("")) }

    return part1
}