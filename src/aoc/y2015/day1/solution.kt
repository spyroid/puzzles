package aoc.y2015.day1

import gears.puzzle

fun main() {
    puzzle { part1("))(((((") }
    puzzle { part1(input()) }
    puzzle { part2("()())") }
    puzzle { part2(input()) }
}

private fun part1(all: String) = all.map { if (it == '(') 1 else -1 }.sum()

private fun part2(all: String) = all.map { if (it == '(') 1 else -1 }
    .foldIndexed(Pair(0, 0)) { i, acc, c -> if (acc.second < 0) acc else Pair(i + 1, acc.second + c) }.first

