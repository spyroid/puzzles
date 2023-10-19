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

    fun neighbors() = Direction.entries.map { this + it }
    fun neighbors(bounds: Point) = Direction.entries.map { this + it }.filter { it.withinBounds(bounds) }

    fun neighbors9() = (-1..1)
        .flatMap { x -> (-1..1).map { y -> Point(x, y) } }
        .filter { it.x != 0 || it.y != 0 }
        .map { this + it }

    fun around8() = Direction.entries.take(8).map { this + it }
    fun around4() = Direction.entries.take(4).map { this + it }

    companion object {
        val zero = Point(0, 0)

        fun fromStr(str: String, delim: String = ","): Point {
            val split = str.split(delim)
            return Point(split[0].toInt(), split[1].toInt())
        }
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
