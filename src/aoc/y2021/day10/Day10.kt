package aoc.y2021.day10

import gears.puzzle
import kotlin.math.absoluteValue

fun main() {
    puzzle { part1(linesFrom("input.txt")) }
    puzzle { part2(linesFrom("input.txt")) }
    puzzle { parts12(linesFrom("input.txt")) }
}

private val pairs = mapOf('}' to '{', '>' to '<', ')' to '(', ']' to '[')
private val scores = mapOf('}' to 1197L, '>' to 25137L, ')' to 3L, ']' to 57L)
private val scores2 = mapOf('{' to 3L, '<' to 4L, '(' to 1L, '[' to 2L)

private fun validate(line: String): Long {
    val stack = ArrayDeque<Char>()
    for (c in line) {
        if (pairs.containsValue(c)) stack.addFirst(c) else {
            if (pairs[c] == stack.first()) stack.removeFirst() else return -scores[c]!!
        }
    }
    return stack.map { scores2[it]!! }.fold(0) { acc, i -> acc * 5 + i }
}

private fun part1(input: List<String>) = input.map { validate(it) }.filter { it < 0 }.sumOf { it }.absoluteValue

private fun part2(input: List<String>) = input.map { validate(it) }.filter { it > 0 }.sorted().let { it[it.size / 2] }

private fun parts12(input: List<String>) = input
    .map { validate(it) }
    .groupBy { it < 0 }
    .let { g -> Pair(g[true]!!.sumOf { it }.absoluteValue, g[false]!!.sorted().let { list -> list[list.size / 2] }) }

