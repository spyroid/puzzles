package aoc.y2024.day20

import gears.Grid
import gears.Point
import gears.puzzle

fun main() {
    puzzle { raceCondition(inputLines()) }

}

private fun raceCondition(input: List<String>): Any {

    val grid = Grid.of(input) { it }
    var start = grid.all().first { it.v == 'S' }.p
    var end = grid.all().first { it.v == 'E' }.p

    fun find(s: Point, e: Point): Pair<MutableMap<Point, Int>, Int> {
        val pq = ArrayDeque<Grid.Entry<Int>>().apply { add(Grid.Entry(s, 0)) }
        val d = mutableMapOf<Point, Int>()
        var best = -1

        while (pq.isNotEmpty()) {
            val (point, score) = pq.removeFirst()
            if (d.containsKey(point)) continue
            d[point] = score
            if (point == e) best = score
            grid
                .around4(point)
                .filter { it.v != '#' }
                .forEach { pq.addFirst(Grid.Entry(it.p, score + 1)) }
        }
        return d to best
    }

    val (distStart, score0) = find(start, end) // start, end
    val (distEnd, _) = find(end, start)

    fun cheats(threshold: Int, saveSeconds: Int) = grid.all()
        .filter { (p, v) -> v != '#' && p in distStart }
        .sumOf { (pos, _) ->
            pos.allManhattan(threshold)
                .filter {
                    grid.entryAt(it).let { it != null && it.v != '#' && it.p in distEnd }
                }
                .map { p ->
                    val dist = distStart.getValue(pos) + distEnd.getValue(p) + p.manhattan(pos)
                    if (dist <= score0 - saveSeconds) 1 else 0
                }.sum()
        }

    return cheats(2, 100) to cheats(20, 100)
}
