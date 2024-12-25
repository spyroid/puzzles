package aoc.y2024.day25

import gears.puzzle

fun main() {
    puzzle { codeChronicle(input()) }
}

private fun codeChronicle(input: String): Any {
    val blocks = input.replace('#', '1').replace('.', '0').split("\n\n")

    val (locks, keys) = blocks.map { block ->
        val a = block.lines().map { s -> s.map { it.digitToInt() } }.reduce { a, b -> a.zip(b).map { it.first + it.second } }
        a to block.first()
    }.groupBy { it.second }.mapValues { it.value.map { it.first } }.values.toList()

    val c = locks.map { lock ->
        keys.map { key ->
            lock.zip(key).map { (a, b) -> a + b }.all { it <= 7 }
        }
    }.flatten().count { it }

    return c
}
