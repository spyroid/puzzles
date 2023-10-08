package aoc.y2017.day17

import gears.puzzle

private fun main() {
    puzzle { spinlock(377) }
}

private fun spinlock(step: Int): Any {
    val spin = mutableListOf(0)
    var (pos, part1, part2) = listOf(0, 0, 0)

    for (i in 1..50_000_000) {
        pos = (pos + step) % i + 1
        if (pos == 1) part2 = i
        if (pos <= 2017) spin.add(pos, i)
        if (i == 2017) part1 = spin[pos + 1]
    }
    return part1 to part2
}
