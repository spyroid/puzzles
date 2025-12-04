package aoc.y2025.day1

import gears.puzzle
import kotlin.math.absoluteValue

fun main() {
    puzzle {
        secretEntrance(inputLines())
    }
}

private fun secretEntrance(input: List<String>): Any {
    val numbers = input.map { it.replace("L", "-").replace("R", "").toInt() }
    val part1 = numbers.runningFold(50) { acc, delta -> (acc + delta).mod(100) }.count { it == 0 }

    val part2 = numbers.runningFold(Pair(50, 0)) { acc, delta ->
        val corrected = if (delta > 0) acc.first else (100 - acc.first) % 100
        val zeroPasses = (corrected + delta.absoluteValue) / 100
        val newValue = (acc.first + delta).mod(100)
        Pair(newValue, zeroPasses)
    }
        .sumOf { it.second }

    return part1 to part2
}