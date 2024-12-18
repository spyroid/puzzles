package aoc.y2024.day18

import gears.Grid
import gears.Point
import gears.puzzle

fun main() {
    puzzle { RAMRun(inputLines()) }
}

private fun RAMRun(input: List<String>): Any {

    val allWalls = input.map { it.split(",").let { (a, b) -> Point(a.toInt(), b.toInt()) } }

    fun bfs(walls: Set<Point>, start: Point, end: Point): Int {
        val queue = ArrayDeque<Grid.Entry<Int>>().apply { add(Grid.Entry(start, 0)) }
        val visited = mutableSetOf<Point>()
        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            if (current.p == end) return current.v
            current.p.around4()
                .filter { it.withinBounds(end) && !visited.contains(it) && !walls.contains(it) }
                .forEach {
                    queue.add(Grid.Entry(it, current.v + 1))
                    visited.add(it)
                }
        }
        return -1
    }

    val part1 = bfs(allWalls.take(1024).toSet(), Point.zero, Point(70, 70))

    val currentWalls = mutableSetOf<Point>()
    for (wall in allWalls) {
        currentWalls.add(wall)
        if (bfs(currentWalls, Point.zero, Point(70, 70)) == -1) break
    }

    return part1 to currentWalls.last()
}

