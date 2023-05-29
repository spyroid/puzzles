package ms

import gears.puzzle

fun main() {
    puzzle { solution("-H-HH--") }
    puzzle { solution("H") }
    puzzle { solution("H-") }
    puzzle { solution("HH-HH") }
    puzzle { solution("-H-H-H-H-H") }
    puzzle { solution("--") }
    puzzle { solution("HHH") }
}

private fun solution(s: String): Int {
    if (s.length < 2) return -1
    val list = s.toMutableList()

    list.windowed(3).onEachIndexed { i, sub ->
        if (sub == listOf('H', '-', 'H')) if (!list.hasAround(i) && !list.hasAround(i + 2)) list[i + 1] = '*'
    }
    list.windowed(2).onEachIndexed { i, sub ->
        if (sub == listOf('-', 'H') && !list.hasAround(i + 1)) list[i] = '*'
        if (sub == listOf('H', '-') && !list.hasAround(i)) list[i + 1] = '*'
    }
    list.windowed(2).onEachIndexed { i, sub ->
        if (sub == listOf('H', 'H') && (!list.hasAround(i) || !list.hasAround(i + 1))) return -1
    }
    val count = list.count { it == '*' }
    return if (count == 0) -1 else count
}

private fun List<Char>.hasAround(idx: Int): Boolean {
    if (idx < 0 || idx > this.lastIndex || this[idx] != 'H') return false
    if (this.getOrElse(idx - 1) { _ -> '.' } == '*') return true
    return this.getOrElse(idx + 1) { _ -> '.' } == '*'
}
