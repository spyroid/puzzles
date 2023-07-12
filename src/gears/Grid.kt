package gears

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

class Grid<T> {
    operator fun set(p: Point, value: T) {
        this.grid[p.y][p.x] = value
    }

    operator fun set(x: Int, y: Int, value: T) {
        this.grid[y][x] = value
    }

    operator fun get(p: Point): T {
        return this.grid[p.y][p.x]
    }

    operator fun get(x: Int, y: Int): T {
        return this.grid[y][x]
    }

    private var grid: MutableList<MutableList<T>>
    fun data() = grid

    companion object {
        fun <T> of(input: List<String>, mapper: (Char) -> T): Grid<T> {
            return Grid(input.map { row -> row.map(mapper).toMutableList() }.toMutableList())
        }
    }

    private constructor(grid: MutableList<MutableList<T>>) {
        this.grid = grid
    }

    fun rotate2D(): Grid<T> {
        val grid = MutableList(this.grid[0].size) { i -> MutableList(this.grid.size) { j -> this.grid[j][i] } }
        return Grid(grid)
    }

    fun at(x: Int, y: Int): T? = if (y in this.grid.indices && x in this.grid.first().indices) this.grid[y][x] else null
    fun at(p: Point): T? = at(p.x, p.y)
    fun pointAt(x: Int, y: Int): Point? =
        if (y in this.grid.indices && x in this.grid.first().indices) Point(x, y) else null

    fun around(x: Int, y: Int): List<T> {
        return listOf(
            at(x - 1, y),
            at(x + 1, y),
            at(x, y + 1),
            at(x, y - 1)
        ).mapNotNull { it }
    }

    fun pointsAround(p: Point) = pointsAround(p.x, p.y)

    fun pointsAround(x: Int, y: Int): List<Point> {
        return listOf(
            pointAt(x - 1, y),
            pointAt(x + 1, y),
            pointAt(x, y + 1),
            pointAt(x, y - 1)
        ).mapNotNull { it }
    }

    fun pointsOf(body: (x: Int, y: Int, v: T) -> Boolean): List<Point> {
        val res = mutableListOf<Point>()
        for (y in this.grid.indices) {
            for (x in this.grid[y].indices) {
                if (body(x, y, this.grid[y][x])) res.add(Point(x, y))
            }
        }
        return res
    }

    fun around8(x: Int, y: Int): List<T> {
        return listOf(
            at(x - 1, y),
            at(x + 1, y),
            at(x, y + 1),
            at(x - 1, y + 1),
            at(x + 1, y + 1),
            at(x, y - 1),
            at(x - 1, y - 1),
            at(x + 1, y - 1),
        ).mapNotNull { it }
    }

    fun clone(transformer: Grid<T>.(x: Int, y: Int, e: T) -> T): Grid<T> {
        val cloned = Grid(MutableList(data().size) { data()[it].toMutableList() })
        for (y in data().indices) {
            for (x in data()[y].indices) {
                val v = transformer(x, y, this[x, y])
                cloned[x, y] = v
            }
        }
        return cloned
    }

    override fun toString(): String {
        val data = data()
        return buildString {
            for (line in data) {
                line.forEach { this.append(it) }
                append("\n")
            }
        }
    }

    fun all(): Sequence<T> {
        return sequence {
            for (line in data()) line.forEach { yield(it) }
        }
    }
}

data class Point(val x: Int, val y: Int) {
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

    fun neighbors() = Direction.values().map { this + it }
    fun neighbors(bounds: Point) = Direction.values().map { this + it }.filter { it.withinBounds(bounds) }

    fun neighbors9() = (-1..1)
        .flatMap { x -> (-1..1).map { y -> Point(x, y) } }
        .filter { it.x != 0 || it.y != 0 }
        .map { this + it }

    companion object {
        val zero = Point(0, 0)

        fun fromStr(str: String, delim: String = ","): Point {
            val split = str.split(delim)
            return Point(split[0].toInt(), split[1].toInt())
        }

        fun String.toIntVec(delim: String = ",") = fromStr(this, delim)

        /**
         * Calculates the min and max of x and y for all the vectors
         */
        fun Iterable<Point>.bounds() = this.fold(
            listOf(
                Int.MAX_VALUE,
                Int.MIN_VALUE,
                Int.MAX_VALUE,
                Int.MIN_VALUE
            )
        ) { acc, vec ->
            listOf(
                min(acc[0], vec.x),
                max(acc[1], vec.x),
                min(acc[2], vec.y),
                max(acc[3], vec.y)
            )
        }

        fun Iterable<Point>.showAsGrid(char: Char = fullBlock): String {
            val (minX, maxX, minY, maxY) = this.bounds()
            return this.showAsGrid(minX..maxX, minY..maxY, char)
        }

        fun Iterable<Point>.showAsGrid(xRange: IntRange, yRange: IntRange, char: Char = fullBlock): String {
            return yRange.joinToString("\n") { y ->
                xRange.joinToString("") { x ->
                    if (Point(x, y) in this) {
                        char.toString()
                    } else {
                        " "
                    }
                }
            }
        }

    }
}

enum class Direction(var x: Int, var y: Int) {
    RIGHT(1, 0),
    DOWN(0, -1),
    DOWN_RIGHT(1, -1),
    DOWN_LEFT(-1, -1),
    LEFT(-1, 0),
    UP(0, 1),
    UP_RIGHT(1, 1),
    UP_LEFT(-1, 1),
    NOTHING(0, 0);

    fun turnCw() =
        when (this) {
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
            UP -> RIGHT
            else -> NOTHING
        }

    fun turnCcw() =
        when (this) {
            RIGHT -> UP
            DOWN -> RIGHT
            LEFT -> DOWN
            UP -> LEFT
            else -> NOTHING
        }

    fun flip() =
        when (this) {
            RIGHT -> LEFT
            LEFT -> RIGHT
            UP -> DOWN
            DOWN -> UP
            else -> NOTHING
        }

    fun asPoint() = Point(x, y)

    companion object {
        fun of(s: String) = of(s.getOrElse(0) { '!' })
        fun of(ch: Char) =
            when (ch) {
                'N', 'U' -> UP
                'S', 'D' -> DOWN
                'E', 'R', 'F' -> RIGHT
                'W', 'L' -> LEFT
                else -> NOTHING
            }

        fun setYDown() {
            DOWN.y = 1
            UP.y = -1
        }
    }
}

const val fullBlock = '█'
