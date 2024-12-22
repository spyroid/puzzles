package aoc.y2024.day22

import gears.puzzle

fun main() {
    puzzle { monkeyMarket(inputLines()) }
}

private fun monkeyMarket(input: List<String>): Any {

    fun mix(a: Long, b: Long) = (a xor b) % 16777216L
    fun next(input: Long): Long {
        var secret = mix(input * 64, input)
        secret = mix(secret / 32, secret)
        secret = mix(secret * 2048, secret)
        return secret
    }

    val part1 = input.sumOf { generateSequence(next(it.toLong())) { next(it) }.take(2000).last() }

    return part1
}

