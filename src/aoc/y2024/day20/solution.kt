package aoc.y2024.day20

import gears.Grid
import gears.Point
import gears.puzzle
import gears.range
import kotlin.math.max
import kotlin.math.min

fun main() {
    puzzle { raceCondition(inputLines()) }
    puzzle { raceCondition(inputLines()) }
    puzzle { raceCondition(inputLines()) }
}

private fun raceCondition(input: List<String>): Any {

    val grid = Grid.of(input) { it }
    var start = grid.all().first { it.v == 'S' }.p
    var end = grid.all().first { it.v == 'E' }.p

    fun bfs(s: Point, e: Point): Pair<MutableMap<Point, Int>, Int> {
        val pq = ArrayDeque<Grid.Entry<Int>>().apply { add(Grid.Entry(s, 0)) }
        val d = mutableMapOf<Point, Int>()
        var best = -1

        while (pq.isNotEmpty()) {
            val (point, score) = pq.removeFirst()
            if (d.containsKey(point)) continue
            d[point] = score
            if (point == end) best = score
            grid.around4(point).filter { it.v != '#' }.forEach { pq.addFirst(Grid.Entry(it.p, score + 1)) }
        }
        return d to best
    }

    val (distStart, score0) = bfs(start, end) // start, end
    val (distEnd, _) = bfs(end, start)

    fun cheats(threshold: Int, saveSeconds: Int): Int {
        var res = 0

        for (i in 0..grid.maxY()) for (j in 0..grid.maxX()) {
            val pos = Point(j, i)
            if (grid[pos] == '#' || pos !in distStart) continue

            for (k in range(max(i - threshold, 0), min(i + threshold + 1, grid.maxY())))
                for (l in range(max(j - threshold, 0), min(j + threshold + 1, grid.maxX()))) {
                    val p = Point(l, k)
                    var dist = pos.manhattan(p)
                    if (dist > threshold || grid[p] == '#' || p !in distEnd.keys) continue
                    dist = distStart.getValue(pos) + distEnd.getValue(p) + dist
                    if (dist <= score0 - saveSeconds) res += 1
                }
        }
        return res
    }

    return cheats(2, 100) to cheats(20, 100)
}
