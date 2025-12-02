package aoc.y2025.day2

import gears.puzzle
import gears.toDigits

fun main() {
    puzzle {
        giftShop(input())
    }
}

private fun giftShop(input: String): Any {
    val ranges = input.split(",").map { LongRange(it.substringBefore("-").toLong(), it.substringAfter("-").toLong()) }
    val part1 = ranges.sumOf { range -> range.filter { isBad(it, true) }.sum() }
    val part2 = ranges.sumOf { range -> range.filter { isBad(it) }.sum() }
    return part1 to part2
}

private fun isBad(value: Long, justHalf: Boolean = false): Boolean {
    val numbers = value.toDigits().toList()
    if (justHalf) if (numbers.size % 2 != 0) return false
    val segments = if (justHalf) listOf(numbers.size / 2) else (1..numbers.size).filter { numbers.size % it == 0 && it < numbers.size / 2 + 1 }
    return segments.any { isEquals(numbers.windowed(it, it)) }
}

private fun isEquals(data: List<List<Long>>) = data.drop(1).any { it != data.first() }