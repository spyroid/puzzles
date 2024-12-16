package aoc.y2024.day16

import gears.Direction
import gears.Direction.RIGHT
import gears.Grid
import gears.Point
import gears.puzzle

fun main() {
    puzzle { reindeerMaze(inputLines()) }
}

private fun reindeerMaze(input: List<String>): Any {
    val grid = Grid.of(input) { it }
    val start = grid.all().first { it.v == 'S' }.also { grid[it.p] = '.' }
    val end = grid.all().first { it.v == 'E' }.also { grid[it.p] = '.' }

    fun findPaths(start: Point, end: Point): Pair<MutableList<MutableList<Point>>, Int> {
        val stack = ArrayDeque<Item>().apply { add(Item(start, RIGHT)) }
        val seen = mutableMapOf<Pair<Point, Direction>, Int>()
        val paths = mutableListOf<MutableList<Point>>()
        var minCost = Int.MAX_VALUE

        while (stack.isNotEmpty()) {
            var cur = stack.removeFirst()
            cur.path.add(cur.p)
            if (cur.p == end) {
                if (cur.cost < minCost) paths.clear().also { minCost = cur.cost }
                if (cur.cost == minCost) paths.add(cur.path)
                continue
            }

            if (seen.getOrPut(cur.p to cur.dir) { cur.cost } < cur.cost || cur.cost > minCost) continue

            grid.around4(cur.p).filter { it.v != '#' }.forEach { e ->
                val d = Direction.all4().first { cur.p + it == e.p }
                stack.add(Item(e.p, d, cur.path.toMutableList(), cur.cost + (if (cur.dir == d) 1 else 1_001)))
            }
        }
        return paths to minCost
    }

    val (minPaths, minCost) = findPaths(start.p, end.p)
    return minCost to minPaths.flatten().toSet().count()
}

private data class Item(val p: Point, val dir: Direction, val path: MutableList<Point> = mutableListOf(), var cost: Int = 0)