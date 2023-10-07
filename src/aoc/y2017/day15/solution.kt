package aoc.y2017.day15

import gears.puzzle

private fun main() {
    puzzle { duel(618, 814) }
    puzzle { duel2(618, 814) }
}

private fun gen(start: Long, factor: Long) = generateSequence(start) { l -> l * factor % 2147483647 }

private fun duel(a: Long, b: Long): Any {
    return gen(a, 16807).zip(gen(b, 48271))
        .map { (a, b) -> a.and(0xffff) == b.and(0xffff) }
        .take(40_000_000)
        .count { it }
}

private fun duel2(a: Long, b: Long): Any {
    return gen(a, 16807).filter { it % 4 == 0L }.zip(gen(b, 48271).filter { it % 8 == 0L })
        .map { (a, b) ->
            a.and(0xffff) == b.and(0xffff)
        }
        .take(5_000_000)
        .count { it }
}
