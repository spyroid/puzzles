package aoc.y2022.day2

import gears.puzzle

fun main() {
    puzzle { getScore(linesFrom("day2.test.txt"), strategy1) }
    puzzle { getScore(linesFrom("day2.input.txt"), strategy1) }
    puzzle { getScore(linesFrom("day2.test.txt"), strategy2) }
    puzzle { getScore(linesFrom("day2.input.txt"), strategy2) }
}

private val strategy1 = mapOf(
    'A' to mapOf('X' to 1 + 3, 'Y' to 2 + 6, 'Z' to 3 + 0),
    'B' to mapOf('X' to 1 + 0, 'Y' to 2 + 3, 'Z' to 3 + 6),
    'C' to mapOf('X' to 1 + 6, 'Y' to 2 + 0, 'Z' to 3 + 3)
)

private val strategy2 = mapOf(
    'A' to mapOf('X' to 3 + 0, 'Y' to 1 + 3, 'Z' to 2 + 6),
    'B' to mapOf('X' to 1 + 0, 'Y' to 2 + 3, 'Z' to 3 + 6),
    'C' to mapOf('X' to 2 + 0, 'Y' to 3 + 3, 'Z' to 1 + 6)
)

private fun getScore(input: List<String>, strategy: Map<Char, Map<Char, Int>>) = input.sumOf { strategy[it[0]]?.get(it[2]) ?: 0 }
