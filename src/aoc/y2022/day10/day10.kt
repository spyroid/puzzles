package aoc.y2022.day10

import puzzle

fun main() {
    puzzle("test 1") { crt(linesFrom("test.txt")) }
    puzzle("1") { crt(linesFrom("input.txt")) }
}

private fun crt(input: List<String>): Int {
    return sequence {
        input.forEach { s -> yield(0).also { if (s.startsWith("addx")) yield(s.drop(5).toInt()) } }
    }.foldIndexed(Pair(1, 0)) { i, acc, v ->
        val strength = if (i + 1 == 20 || (i - 19) % 40 == 0) acc.first * (i + 1) else 0
        Pair(acc.first + v, acc.second + strength).also {
            if (i % 40 == 0) println()
            if (i % 40 + 1 in acc.first..acc.first + 2) print("#") else print(".")
        }
    }.second.also { println() }
}
