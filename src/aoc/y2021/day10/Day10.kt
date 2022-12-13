package aoc.y2021.day10

import gears.readInput
import kotlin.math.absoluteValue
import kotlin.system.measureTimeMillis

fun main() {

    val pairs = mapOf('}' to '{', '>' to '<', ')' to '(', ']' to '[')
    val scores = mapOf('}' to 1197L, '>' to 25137L, ')' to 3L, ']' to 57L)
    val scores2 = mapOf('{' to 3L, '<' to 4L, '(' to 1L, '[' to 2L)

    fun validate(line: String): Long {
        val stack = ArrayDeque<Char>()
        for (c in line) {
            if (pairs.containsValue(c)) stack.addFirst(c) else {
                if (pairs[c] == stack.first()) stack.removeFirst() else return -scores[c]!!
            }
        }
        return stack.map { scores2[it]!! }.fold(0) { acc, i -> acc * 5 + i }
    }

    fun part1(input: List<String>) = input.map { validate(it) }.filter { it < 0 }.sumOf { it }.absoluteValue

    fun part2(input: List<String>) = input.map { validate(it) }.filter { it > 0 }.sorted().let { it[it.size / 2] }

    fun parts12(input: List<String>) = input
        .map { validate(it) }
        .groupBy { it < 0 }
        .let { g -> Pair(g[true]!!.sumOf { it }.absoluteValue, g[false]!!.sorted().let { list -> list[list.size / 2] }) }



    val testData = readInput("day10/test")
    val inputData = readInput("day10/input")

    var res1 = part1(testData)
    check(res1 == 26397L) { "Expected 26397 but got $res1" }

    var time = measureTimeMillis { res1 = part1(inputData) }
    println("Part1: $res1 in $time ms")

    res1 = part2(testData)
    check(res1 == 288957L) { "Expected 288957 but got $res1" }

    time = measureTimeMillis { res1 = part2(inputData) }
    println("Part2: $res1 in $time ms")

    var res2: Pair<Long, Long>
    time = measureTimeMillis { res2 = parts12(inputData) }
    println("Parts 1&2: Corrupted ${res2.first}, Incompleted ${res2.second} in $time ms")
}

