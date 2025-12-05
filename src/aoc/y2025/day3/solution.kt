package aoc.y2025.day3

import gears.puzzle
import gears.toDigits

fun main() {
    puzzle {
        lobby(inputLines())
    }
}

private fun lobby(input: List<String>): Any {
    fun maxFrom(str: String) = str.dropLast(1).indices.maxBy { str[it] }
        .let { a ->
            str.drop(a + 1).maxOf { it }.let { b -> "${str[a]}$b".toInt() }
        }

    val part1 = input.sumOf { maxFrom(it) }

    return part1
}
