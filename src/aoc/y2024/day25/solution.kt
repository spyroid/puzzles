package aoc.y2024.day25

import gears.puzzle

fun main() {
    puzzle { codeChronicle(input()) }
}

private fun codeChronicle(input: String) = input.split("\n\n").map { block ->
    block.lines().map { s -> s.map { if (it == '#') 1 else 0 } }
        .reduce { a, b -> a.zip(b).map { it.first + it.second } } to block.first()
}.groupBy({ it.second }, { it.first }).values.toList()
    .let { (locks, keys) ->
        locks.map { lock ->
            keys.map { key -> lock.zip(key).map { (a, b) -> a + b }.all { it <= 7 } }
        }.flatten().count { it }
    }
