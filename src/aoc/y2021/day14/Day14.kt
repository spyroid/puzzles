package aoc.y2021.day14

import com.github.ajalt.mordant.terminal.Terminal
import readInput
import kotlin.system.measureTimeMillis

fun main() {

    class Polymer(input: List<String>) {
        var template = input.first()

        val pairCounts = input.first().asSequence()
            .windowed(2)
            .map { it.joinToString("") }
            .groupingBy { it }
            .eachCount()
            .mapValues { (_, v) -> v.toLong() }

        val spawnRules = input.drop(2).associate {
            it.split(" -> ").let { (pair, insertion) ->
                pair to Pair(pair.first() + insertion, insertion + pair.last())
            }
        }

        fun calculate(iterations: Int): Long {
            return generateSequence(pairCounts) { getNewCounts(it) }
                .drop(iterations)
                .first()
                .let { countLetters(it) }
                .values
                .sortedBy { it }.let { it.last() - it.first() }
        }

        private fun countLetters(pairCounts: Map<String, Long>): Map<Char, Long> {
            val letterCounts = mutableMapOf<Char, Long>().apply {
                pairCounts.entries.forEach { (key, value) ->
                    this[key.first()] = getOrDefault(key.first(), 0) + value
                    this[key.last()] = getOrDefault(key.last(), 0) + value
                }
            }
            return letterCounts.entries.associate { (key, value) ->
                when (key) {
                    template.first(), template.last() -> key to (value + 1) / 2
                    else -> key to value / 2
                }
            }
        }

        private fun getNewCounts(pairCounts: Map<String, Long>): Map<String, Long> {
            return mutableMapOf<String, Long>().apply {
                pairCounts.forEach { pair ->
                    val pp = spawnRules[pair.key]
                    if (pp != null) {
                        this[pp.first] = getOrDefault(pp.first, 0L) + pair.value
                        this[pp.second] = getOrDefault(pp.second, 0L) + pair.value
                    }
                }
            }
        }
    }

    fun part1(input: List<String>) = Polymer(input).calculate(10)

    fun part2(input: List<String>) = Polymer(input).calculate(40)


    val testData = readInput("day14/test")
    val inputData = readInput("day14/input")

    val term = Terminal()

    var res1 = part1(testData)
    check(res1 == 1588L) { "Expected 1588 but got $res1" }

    var time = measureTimeMillis { res1 = part1(inputData) }
    term.success("⭐️ Part1: $res1 in $time ms")

    time = measureTimeMillis { res1 = part2(inputData) }
    term.success("⭐️ Part2: $res1 in $time ms")
}

