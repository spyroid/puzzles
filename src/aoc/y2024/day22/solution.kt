package aoc.y2024.day22

import gears.puzzle

fun main() {
    puzzle { monkeyMarket(inputLines()) }
}

private fun monkeyMarket(input: List<String>): Any {

    fun mix(a: Long, b: Long) = (a xor b) % 16777216L
    fun next(input: Long) = mix(input * 64, input).let { mix(it / 32, it) }.let { mix(it * 2048, it) }

    val part1 = input.sumOf { generateSequence(next(it.toLong())) { next(it) }.take(2000).last() }

    val total = mutableMapOf<List<Int>, Long>()
    for (secret in input) {
        generateSequence(secret.toLong()) { next(it) }
            .map { (it % 10).toInt() }
            .zipWithNext { a, b -> b - a to b }
            .take(2000)
            .windowed(4)
            .map { it.unzip().let { (diffs, prices) -> diffs to prices.last() } }
            .distinctBy { it.first }
            .forEach { (diffs, price) -> total[diffs] = total.getOrDefault(diffs, 0) + price }
    }
    val part2 = total.maxBy { it.value }

    return part1 to part2
}
