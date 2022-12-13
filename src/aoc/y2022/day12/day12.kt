package aoc.y2022.day12

import gears.Grid
import gears.Point
import gears.puzzle

fun main() {
    puzzle("1") { part1(linesFrom("test.txt"), true) }
    puzzle("1") { part1(linesFrom("input.txt"), true) }
    puzzle("2") { part1(linesFrom("test.txt"), false) }
    puzzle("2") { part1(linesFrom("input.txt"), false) }
}

private fun part1(input: List<String>, directionUp: Boolean): Int {
    val map = Grid.of(input) { c -> c }
    val start = map.pointsOf { _, _, v -> v == 'S' }.onEach { map[it] = 'a' }.first()
    var goal = map.pointsOf { _, _, v -> v == 'E' }.onEach { map[it] = 'z' }.first()
    return if (directionUp)
        hill(map, start, goal, true) { p -> p == goal }.count() - 1
    else
        hill(map, goal, start, false) { p -> map.at(p)!! == 'a' }.count() - 1
}

private fun hill(map: Grid<Char>, start: Point, goal: Point, directionUp: Boolean, rule: (Point) -> Boolean): Sequence<Point> {
    val points = mutableMapOf<Point, Point>()
    var seen = mutableSetOf(start)
    val queue = mutableListOf(start)
    var current = Point(-1, -1)

    while (queue.isNotEmpty()) {
        current = queue.removeFirst()
        if (rule(current)) break

        map.pointsAround(current)
            .filter { it !in seen }
            .filter { (if (directionUp) map.at(it)!! - map.at(current)!! else map.at(current)!! - map.at(it)!!) <= 1 }
            .forEach {
                points[it] = current
                seen.add(it)
                queue.add(it)
            }
    }
    return generateSequence(current) { points[it] }
}
