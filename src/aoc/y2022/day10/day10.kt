package aoc.y2022.day10

import gears.fullBlock
import gears.puzzle
import kotlin.math.abs

fun main() {
    puzzle { crt(linesFrom("test.txt")) }
    puzzle { crt(linesFrom("input.txt")) }
}

private fun crt(input: List<String>): Int {
    return input.flatMap { it.split(" ") }
        .map { if (it.last().isDigit()) it.toInt() else 0 }
        .scan(1, Int::plus).dropLast(1)
        .onEachIndexed { i, x ->
            if (i % 40 == 0) println()
            if (i % 40 + 1 in x..x + 2) print(fullBlock) else print(".")
        }
        .mapIndexed { i, x -> if (abs(i - 19) % 40 == 0) x * (i + 1) else 0 }
        .sumOf { it }.also { println() }
}
