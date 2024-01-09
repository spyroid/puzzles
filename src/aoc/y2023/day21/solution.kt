package aoc.y2023.day21

import gears.Grid
import gears.Point
import gears.puzzle

private fun main() {
    puzzle("1 & 2") { stepCounter(inputLines()) }
}

private fun stepCounter(input: List<String>): Any {
    val garden = Grid.of(input) { it }
    val size = garden.data().size
    val start = garden.all().first { it.v == 'S' }.p

    fun findPaths(maxDistance: Int): Map<Point, Long> = buildMap {
        val q = mutableListOf<Pair<Point, Long>>().apply { add(start to 0L) }
        while (q.isNotEmpty()) {
            val (p, d) = q.removeFirst()
            if (p in this || d > maxDistance) continue
            this[p] = d
            garden.gvAround(p).filter { it.v != '#' && it.p !in this }.map { it.p to d + 1 }.let { q.addAll(it) }
        }
    }

    val part1 = findPaths(size / 2).values.count { it % 2 == 0L }

    // @see https://github.com/villuna/aoc23/wiki/A-Geometric-solution-to-advent-of-code-2023,-day-21
    val paths = findPaths(size + 1)
    val oddCorners = paths.count { it.value % 2 == 1L && it.value > size / 2 + 1 }.toLong()
    val evenCorners = paths.count { it.value % 2 == 0L && it.value > size / 2 + 1 }.toLong()
    val evenBlock = paths.values.count { it % 2 == 0L }.toLong()
    val oddBlock = paths.values.count { it % 2 == 1L }.toLong()
    val n = (26501365L - (size / 2)) / size

    val even = n * n
    val odd = (n + 1) * (n + 1)
    val part2 = (odd * oddBlock) + (even * evenBlock) - ((n + 1) * oddCorners) + (n * evenCorners)
    return part1 to part2
}