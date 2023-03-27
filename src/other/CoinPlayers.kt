package other

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sign

data class Point(val x: Int, val y: Int)
enum class Direction(var x: Int, var y: Int) {
    RIGHT(1, 0),
    DOWN(0, -1),
    DOWN_RIGHT(1, -1),
    DOWN_LEFT(-1, -1),
    LEFT(-1, 0),
    UP(0, 1),
    UP_RIGHT(1, 1),
    UP_LEFT(-1, 1);
}

fun main() {
    fun chebyshevDistance(p1: Point, p2: Point, dir: Direction): Int {
        val dx = p2.x - p1.x
        val dy = p2.y - p1.y

        if ((dir.x * dir.y != 0 && dx != dy) || dx.sign != dir.x || dy.sign != dir.y) return -1
        return max(abs(dx), abs(dy))
    }

    println(chebyshevDistance(Point(0, 0), Point(3, 3), Direction.UP_RIGHT))
    println(chebyshevDistance(Point(0, 0), Point(3, 5), Direction.UP_RIGHT))
    println(chebyshevDistance(Point(1, 2), Point(5, 6), Direction.UP_RIGHT))
    println(chebyshevDistance(Point(1, 1), Point(1, 6), Direction.UP))
}
