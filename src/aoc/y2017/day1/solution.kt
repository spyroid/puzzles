package aoc.y2017.day1

import gears.puzzle

private fun main() {
    puzzle { captcha(inputLines("input.txt").first().map { it.digitToInt() }) }
    puzzle { captcha2(inputLines("input.txt").first().map { it.digitToInt() }) }
}

private fun captcha(items: List<Int>): Int {
    return items
        .zipWithNext { a, b -> if (a - b == 0) a else 0 }
        .sum()
        .let { if (items.first() == items.last()) it + items.first() else it }
}

private fun captcha2(items: List<Int>) =
    (0..items.lastIndex / 2)
        .sumOf { i -> if (items[i] == items[i + items.size / 2]) items[i] else 0 } * 2
