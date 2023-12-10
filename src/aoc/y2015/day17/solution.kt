package aoc.y2015.day17

import gears.puzzle
import gears.toInts

private fun main() {
    puzzle { thing1(25, inputLines("test.txt").toInts()) }
    puzzle { thing1(150, inputLines("input.txt").toInts()) }
    puzzle { thing2(25, inputLines("test.txt").toInts()) }
    puzzle { thing2(150, inputLines("input.txt").toInts()) }
}

private fun thing1(amount: Int, items: List<Int>) = allFiltered(amount, items).count()

private fun thing2(amount: Int, items: List<Int>): Int {
    return allFiltered(amount, items)
        .groupingBy { it.count() }
        .eachCount()
        .entries.minOf { it.key }
}

private fun allFiltered(amount: Int, items: List<Int>): List<List<Int>> {
    return (1..<(1 shl items.size))
        .mapIndexed { i, _ -> items.filterIndexed { idx, _ -> 1 shl idx and i != 0 } }
        .filter { it.sum() == amount }
}
