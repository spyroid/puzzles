package aoc.y2017.day17

import gears.puzzle

private fun main() {
    puzzle { spinlock(377) }
}

private fun spinlock(step: Int): Any {
    val spin = mutableListOf(0)
    var pos = 0

    for (i in 1..2017) {
        pos = (pos + step) % spin.size + 1
        spin.add(pos, i)
    }
    return spin[pos + 1]
}
