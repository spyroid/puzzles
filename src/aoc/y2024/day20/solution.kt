package aoc.y2024.day20

import gears.Grid
import gears.Point
import gears.puzzle

fun main() {
    puzzle { raceCondition(inputLines()) }
}

private fun raceCondition(input: List<String>): Any {

    val grid = Grid.of(input) { it }
    var current = grid.all().first { it.v == 'S' }
    var steps = 1
    val map = mutableMapOf(current.p to 0)
    val portals = mutableMapOf<Point, MutableSet<Point>>()

    while (current.v != 'E') {
        val around = grid.around4(current.p).filter { it.p !in map.keys }
        around.filter { it.v == '#' }
            .mapNotNull { grid.entryAt((it.p - current.p).asDirection() + it.p) }
            .filter { (it.v == '.' || it.v == 'E') && it.p !in map.keys }
            .forEach { portals.getOrPut(current.p) { mutableSetOf() }.add(it.p) }

        current = around.first { (it.v == '.' || it.v == 'E') }
        map[current.p] = steps++
    }

    val part1 = sequence { portals.forEach { (k, v) -> v.forEach { c -> yield(map.getValue(c) - map.getValue(k) - 2) } } }
        .groupingBy { it }.eachCount().filterKeys { it >= 100 }.values.sum()

    return part1
}
