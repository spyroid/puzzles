package aoc.y2023.day14

import gears.Point
import gears.puzzle

private fun main() {
    puzzle("1 & 2") { parabolicReflectorDish(inputLines()) }
}

private fun parabolicReflectorDish(input: List<String>): Any {

    var data = input.flatMapIndexed { y, l ->
        l.mapIndexedNotNull { x, c -> if (c == '.') null else Point(x, y) to c }
    }.toMap(mutableMapOf())

    val (w, h) = data.keys.maxOf { it.x } + 1 to data.keys.maxOf { it.y } + 1

    fun packUp() {
        for (y in 0 until h) for (x in 0 until w) {
            if (data[Point(x, y)] != 'O') continue
            val y2 = (0 until y).reversed().takeWhile { data[Point(x, it)] == null }.lastOrNull() ?: continue
            data -= Point(x, y)
            data[Point(x, y2)] = 'O'
        }
    }

    fun rotateRight() {
        data = data.mapKeysTo(mutableMapOf()) { (k, _) -> Point(h - 1 - k.y, k.x) }
    }

    fun Map<Point, Char>.summarize() = filterValues { it == 'O' }.keys.sumOf { h - it.y }

    val history = mutableListOf<Pair<Any, Int>>()

    var part1 = 0
    var part2 = 0

    while (true) {
        repeat(4) {
            packUp()
            if (history.isEmpty() && it == 0) part1 = data.summarize()
            rotateRight()
        }

        val key = data.entries.toSet() as Any to data.summarize()
        if (key !in history) history.add(key) else {
            val cycleStart = history.indexOf(key)
            val cycleLength = history.size - cycleStart
            part2 = history[cycleStart - 1 + (1_000_000_000 - cycleStart) % cycleLength].second
            break
        }
    }
    return part1 to part2
}
