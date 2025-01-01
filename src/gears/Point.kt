package gears

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

data class Point(var x: Int, var y: Int) {
    var value = 0
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    operator fun plus(dir: Direction) = Point(x + dir.x, y + dir.y)

    operator fun minus(other: Point) = Point(x - other.x, y - other.y)
    operator fun minus(dir: Direction) = Point(x - dir.x, y - dir.y)
    operator fun unaryMinus() = Point(-x, -y)

    operator fun times(factor: Int) = Point(x * factor, y * factor)

    fun manhattan(other: Point) = abs(x - other.x) + abs(y - other.y)
    fun manhattan() = manhattan(zero)
    fun chebyshev(other: Point) = max(abs(x - other.x), abs(y - other.y))
    fun chebyshev() = chebyshev(zero)

    fun asDirection() = Point(x.sign, y.sign)

    fun mirrorX(axis: Int = 0) = Point(axis - (x - axis), y)
    fun mirrorY(axis: Int = 0) = Point(x, axis - (y - axis))

    // Note, it is inclusive, so for indexing reduce with 1
    fun withinBounds(bounds: Point) = withinBounds(0, bounds.x, 0, bounds.y)
    fun withinBounds(minX: Int, maxX: Int, minY: Int, maxY: Int) = x in minX..maxX && y in minY..maxY

    fun allInBounds(b: Point) = allInBounds(b.x, b.y)
    fun allInBounds(x2: Int, y2: Int) = allInBounds(x, y, x2, y2)

    private fun allInBounds(x1: Int, y1: Int, x2: Int, y2: Int) = sequence { for (xx in range(x1, x2)) for (yy in range(y1, y2)) yield(Point(xx, yy)) }
//    private fun allInBounds(x1: Int, y1: Int, x2: Int, y2: Int): MutableList<Point> {
//        var x = min(x1, x2)
//        var y = min(y1, y2)
//        val h = abs(y1 - y2) + 1
//        val w = abs(x1 - x2) + 1
//        val list = MutableList<Point>(w * h) { i ->
//            Point(x + i / w, y + i % w) //                .also { println(it) }
//        }
//        return list
//    }

    fun allChebyshev(radius: Int) = allInBounds(x - radius, y - radius, x + radius, y + radius).filter { it != this }
    fun borderChebyshev(radius: Int) = allInBounds(x - radius, y - radius, x + radius, y + radius).filter { it != this && chebyshev(it) == radius }
    fun allManhattan(radius: Int) = allInBounds(x - radius, y - radius, x + radius, y + radius).filter { it != this && manhattan(it) <= radius }
    fun borderManhattan(radius: Int) = allInBounds(x - radius, y - radius, x + radius, y + radius).filter { it != this && manhattan(it) == radius }
    fun around8() = Direction.all8().map { this + it }
    fun around4() = Direction.all4().map { this + it }

    companion object {
        val zero = Point(0, 0)
        fun fromStr(str: String, delim: String = ",") = Point(str.substringBefore(delim).toInt(), str.substringAfter(delim).toInt())
    }
}

fun Iterable<Point>.bounds() = this.fold(listOf(Int.MAX_VALUE, Int.MIN_VALUE, Int.MAX_VALUE, Int.MIN_VALUE)) { acc, p ->
    listOf(min(acc[0], p.x), max(acc[1], p.x), min(acc[2], p.y), max(acc[3], p.y))
}

fun Iterable<Point>.toStringGrid(char: Char = fullBlock): String {
    val (minX, maxX, minY, maxY) = this.bounds()
    return this.toStringGrid(minX..maxX, minY..maxY, char)
}

fun Iterable<Point>.toStringGrid(xRange: IntRange, yRange: IntRange, char: Char = fullBlock): String {
    val all = this.toSet()
    return yRange.joinToString("\n") { y ->
        xRange.joinToString("") { x -> if (Point(x, y) in all) char.toString() else " " }
    }
}
