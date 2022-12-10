package aoc.y2022.day10

import puzzle

fun main() {
    puzzle("test 1") {
        part1(linesFrom("test.txt"))
    }
    puzzle("1") {
        part1(linesFrom("input.txt"))
    }
}

private fun part1(input: List<String>): Int {
    var x = 1
    var strength = 0
    crt(input).forEachIndexed { i, pair ->
        var cycle = i + 1
        if (cycle == 20 || (cycle - 20) % 40 == 0) { strength += x * cycle }
        var pos = i % 40
        if (pos == 0) println()
        if (pos + 1 in x..x+2) print("#") else print(".")
        x += pair.second
    }
    println()

    return strength
}

private fun crt(input: List<String>) = sequence {
    input.forEach { s ->
        s.split(" ").plus("0").let {
            if (it[0] == "noop") yield(Pair(it[0], 0)) else {
                yieldAll(listOf(Pair(it[0], 0), Pair(it[0], it[1].toInt())))
            }
        }
    }
}
