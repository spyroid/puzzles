package aoc.y2023.day9

import gears.findIntNumbers
import gears.puzzle

private fun main() {
    puzzle("1 & 2") { mirage(inputLines()) }
}

private fun mirage(input: List<String>) = input.map { it.findIntNumbers() }
    .let { list -> list.sumOf { it.guessNext() } to list.sumOf { it.reversed().guessNext() } }

private fun List<Int>.guessNext(): Int {
    val self = mutableListOf(this.last())
    generateSequence(this) { acc ->
        acc.zipWithNext { a, b -> b - a }.also { self.add(it.last()) }
    }.takeWhile { it.any { v -> v != 0 } }.count()
    return self.fold(0) { a, v -> v + a }
}
