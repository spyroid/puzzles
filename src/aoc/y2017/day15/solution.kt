package aoc.y2017.day15

import gears.puzzle

private fun main() {
    puzzle { duel(618, 814) }
    puzzle { duel2(618, 814) }
}

private fun gen(start: Long, factor: Long) = generateSequence(start) { it * factor % 2147483647 }

private fun count(repeat: Int, seq: Sequence<Pair<Long, Long>>) = seq
    .map { (a, b) -> a.and(0xffff) == b.and(0xffff) }
    .take(repeat)
    .count { it }

private fun duel(a: Long, b: Long) = count(40_000_000, gen(a, 16807).zip(gen(b, 48271)))

private fun duel2(a: Long, b: Long) = gen(a, 16807).filter { it % 4 == 0L }.zip(gen(b, 48271).filter { it % 8 == 0L })
    .let { count(5_000_000, it) }
