package aoc.y2017.day15

import gears.puzzle

private fun main() {
    puzzle { duel(618, 814) }
    puzzle { duel2(618, 814) }
}

private fun generator(start: Long, factor: Long) = generateSequence(start) { it * factor % 2147483647 }.map { it.toShort() }

private fun duel(a: Long, b: Long) = generator(a, 16807)
    .zip(generator(b, 48271))
    .take(40_000_000)
    .count { it.first == it.second }

private fun duel2(a: Long, b: Long) = generator(a, 16807)
    .filter { it % 4 == 0 }
    .zip(generator(b, 48271).filter { it % 8 == 0 })
    .take(5_000_000)
    .count { it.first == it.second }
