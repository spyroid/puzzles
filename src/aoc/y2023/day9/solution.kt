package aoc.y2023.day9

import gears.findInts
import gears.puzzle

private fun main() {
    puzzle("1 & 2") { mirage(inputLines()) }
}

private fun mirage(input: List<String>) = input.map { it.findInts() }
    .let { list -> list.sumOf { it.guessNext() } to list.sumOf { it.reversed().guessNext() } }

private fun List<Int>.guessNext() = mutableListOf<Int>().also { list ->
    generateSequence(this) { acc -> acc.zipWithNext { a, b -> b - a }.also { list.add(acc.last()) } }
        .indexOfFirst { it.all { v -> v == 0 } }
}.fold(0) { a, v -> v + a }
