package aoc.y2024.day13

import gears.puzzle

fun main() {
    puzzle { clawContraption(inputLines()) }
}

private fun clawContraption(input: List<String>): Any {

    return input.windowed(3, 4).map { (a, b, p) ->

        val ax = a.substringAfter('X').substringBefore(',').toDouble()
        val ay = a.substringAfter('Y').toDouble()

        val bx = b.substringAfter('X').substringBefore(',').toDouble()
        val by = b.substringAfter('Y').toDouble()

        val px = p.substringAfter("X=").substringBefore(',').toDouble()
        val py = p.substringAfter("Y=").toDouble()

        val p2 = 10_000_000_000_000

        fun solve(ax: Double, bx: Double, ay: Double, by: Double, px: Double, py: Double): Long {
            val det = (ax * by - ay * bx)
            val x = (px * by - py * bx) / det
            val y = (py * ax - px * ay) / det
            return if (x % 1.0 == 0.0 && y % 1.0 == 0.0) x.toLong() * 3 + y.toLong() else 0L
        }

        solve(ax, bx, ay, by, px, py) to solve(ax, bx, ay, by, px + p2, py + p2)
    }.unzip().let { (a, b) -> a.sum() to b.sum() }
}
