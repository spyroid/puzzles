package aoc.y2021.day17

import gears.puzzle
import kotlin.math.absoluteValue

fun main() {
    puzzle { Simulation(48, 70, -189, -148).find().first }
    puzzle { Simulation(48, 70, -189, -148).find().second }
}

private class Simulation(val x1: Int, val x2: Int, val y1: Int, val y2: Int) {

    data class Bullet(var dx: Int, var dy: Int, var x: Int = 0, var y: Int = 0) {
        var maxY = y

        fun step() {
            x += dx
            y += dy--
            if (dx > 0) dx -= 1 else dx = 0
            maxY = maxOf(maxY, y)
        }
    }

    private fun simulate(x: Int, y: Int): Bullet? {
        val b = Bullet(x, y)
        while (b.y >= y1 && b.x <= x2) {
            b.step()
            if (b.x in x1..x2 && b.y in y1..y2) return b
        }
        return null
    }

    fun find(): Pair<Int, Int> {
        var maxY = -1
        var count = 0

        for (x in 1..x2) {
            for (y in y1..y1.absoluteValue) {
                val res = simulate(x, y)
                if (res != null) {
                    maxY = maxOf(maxY, res.maxY)
                    count++
                }
            }
        }
        return Pair(maxY, count)
    }
}
