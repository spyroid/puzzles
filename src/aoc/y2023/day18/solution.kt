package aoc.y2023.day18

import gears.Direction
import gears.Point
import gears.puzzle

private fun main() {
    puzzle("1 & 2") { lavaductLagoon(inputLines()) }
}

private fun lavaductLagoon(input: List<String>): Any {

    var p = Point.zero
    val map = mutableMapOf<Int, MutableSet<Int>>()
    input.map { s ->
        val (d, l) = s.split(" ").let { Direction.of(it[0], true) to it[1].toInt() }
        var pp = p
        repeat(l) {
            map.compute(pp.y) { k, v -> (v ?: mutableSetOf()).apply { add(pp.x) } }
            pp += d.asPoint()
        }
        p = pp
    }

    map.values.onEach { println(it.sorted()) }
    return 0
}
