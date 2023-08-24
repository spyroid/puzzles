package aoc.y2016.day13

import gears.Point
import gears.bitCount1
import gears.puzzle

private fun main() {
    puzzle { maze() }
    puzzle { maze(true) }
}

private fun maze(part2: Boolean = false): Int {
    fun isWall(x: Int, y: Int) = (x * x + 3 * x + 2 * x * y + y + y * y + 1364).bitCount1() % 2 == 0
    val pipe = ArrayDeque<Pair<Point, Int>>().apply { add(Point(1, 1) to 0) }
    val seen = mutableSetOf<Point>()
    while (pipe.isNotEmpty()) {
        val (p, steps) = pipe.removeFirst()
        if (p.x == 31 && p.y == 39 && !part2) return steps
        if (steps > 50 && part2) return seen.size
        seen.add(p)
        p.around4().filter { (x, y) -> x >= 0 && y >= 0 && isWall(x, y) && Point(x, y) !in seen }.forEach { pipe.add(it to steps + 1) }
    }
    return 0
}
