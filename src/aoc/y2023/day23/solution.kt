package aoc.y2023.day23

import gears.Direction
import gears.Grid
import gears.Point
import gears.puzzle

private fun main() {
    puzzle("1") { longWalk(inputLines()) }
    puzzle("2") { longWalk2(inputLines()) }
}

private fun longWalk(input: List<String>): Any {
    val grid = Grid.of(input) { it }
    val start = Point(1, 0)
    val end = Point(grid.maxX - 1, grid.maxY)

    fun around(p: Point) = when (grid[p]) {
        '>' -> listOf(p + Direction.RIGHT)
        '<' -> listOf(p + Direction.LEFT)
        '^' -> listOf(p + Direction.DOWN)
        'v' -> listOf(p + Direction.UP)
        else -> grid.around4(p).filter { it.v != '#' }.map { it.p }
    }

    val paths = mutableSetOf<Int>()
    fun dfs(start: Point, end: Point, dist: Int, seen: MutableSet<Point>) {
        if (start == end) paths.add(dist)
        for (p in around(start).filter { it !in seen }) {
            seen.add(p)
            dfs(p, end, dist + 1, seen)
            seen.remove(p)
        }
    }

    dfs(start, end, 0, mutableSetOf(start))
    return paths.max()
}

private fun longWalk2(input: List<String>): Any {
    val grid = Grid.of(input) { it }
    val start = Point(1, 0)
    val end = Point(grid.maxX - 1, grid.maxY)

    fun around(p: Point) = grid.around4(p).filter { it.v != '#' }.map { it.p }

    val conjunctions = buildSet {
        add(start)
        grid.allPoints().forEach { if (around(it).size > 2) add(it) }
        add(end)
    }

    val graph = buildMap<Point, MutableList<Pair<Point, Int>>> {
        for (c in conjunctions) {
            for (p in around(c)) {
                var (curr, prev, dist) = Triple(p, c, 1)
                while (curr !in conjunctions) {
                    prev = curr.also { curr = around(curr).filter { it != prev }.first() }
                    dist++
                }
                getOrPut(c) { mutableListOf() }.add(curr to dist)
            }
        }
    }

    val paths = mutableSetOf<Int>()
    fun dfs(start: Point, end: Point, dist: Int, seen: MutableSet<Point>) {
        if (start == end) paths.add(dist)
        for ((v, d) in graph[start].orEmpty().filter { it.first !in seen }) {
            seen.add(v)
            dfs(v, end, dist + d, seen)
            seen.remove(v)
        }
    }

    dfs(start, end, 0, mutableSetOf(start))
    return paths.max()
}
