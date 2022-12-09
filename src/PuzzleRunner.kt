import java.io.File
import kotlin.math.*
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

class PuzzleRunner {
    lateinit var local: Any
    private val baseDir = "src."
    private fun localDir(klass: Any) = File((baseDir + klass.javaClass.packageName).replace(".", File.separatorChar.toString()))
    private fun readLocal(klass: Any, fileName: String) = File(localDir(klass), fileName)
    fun linesFrom(filename: String) = readLocal(local, filename).readLines()
}

@OptIn(ExperimentalTime::class)
fun <T> puzzle(title: String = "", code: PuzzleRunner.() -> T): PuzzleRunner {
    return PuzzleRunner().apply {
        this.local = code
        val paddedTitle = title.padStart(15, ' ') + " "
        val timed = measureTimedValue { code.invoke(this) }
        if (timed.value is Unit) {
            println("$paddedTitle⌛️ ${timed.duration}")
        } else {
            val paddedRes = timed.value.toString().padEnd(20)
            println("$paddedTitle${items.random()} $paddedRes ⏳ ${timed.duration}")
        }
    }
}

private val items = listOf(
    "💊", "🎁", "🎉", "🎈", "💣", "⚰️", "💎", "💰", "✈️", "🚀", "🎸", "⚽", "️🍺", "🍪",
    "🍕", "🍔", "🍓", "🍉", "🌶", "🌈", "🔥", "🍄", "🌸", "🌻", "🍀", "🦊", "🐱"
)

infix fun IntRange.isFullyOverlaps(other: IntRange): Boolean = first <= other.first && last >= other.last
infix fun IntRange.isOverlaps(other: IntRange): Boolean = first <= other.last && other.first <= last

typealias Array2d<T> = List<List<T>>

fun <T> Array2d<T>.rotate2D(): Array2d<T> = List(this[0].size) { i -> List(this.size) { j -> this[j][i] } }
fun <T> Array2d<T>.at(x: Int, y: Int): T? = if (y in indices && x in first().indices) this[y][x] else null

// gift from Matsemann
fun <E, F> cartesian(list1: List<E>, list2: List<F>): Sequence<Pair<E, F>> =
    cartesian(listOf(list1, list2)).map { it[0] as E to it[1] as F }

fun <E, F, G> cartesian(list1: List<E>, list2: List<F>, list3: List<G>): Sequence<Triple<E, F, G>> =
    cartesian(listOf(list1, list2, list3)).map { Triple(it[0] as E, it[1] as F, it[2] as G) }

fun <E> cartesian(lists: List<List<E>>): Sequence<List<E>> {
    return sequence {
        val counters = Array(lists.size) { 0 }
        val length = lists.fold(1) { acc, list -> acc * list.size }

        for (i in 0 until length) {
            val result = lists.mapIndexed { index, list ->
                list[counters[index]]
            }
            yield(result)
            for (pointer in lists.size - 1 downTo 0) {
                counters[pointer]++
                if (counters[pointer] == lists[pointer].size) {
                    counters[pointer] = 0
                } else {
                    break
                }
            }
        }
    }
}

enum class Direction(var x: Int, var y: Int) {
    RIGHT(1, 0),
    DOWN(0, -1),
    LEFT(-1, 0),
    UP(0, 1);

    fun turnCw() =
        when (this) {
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
            UP -> RIGHT
        }

    fun turnCcw() =
        when (this) {
            RIGHT -> UP
            DOWN -> RIGHT
            LEFT -> DOWN
            UP -> LEFT
        }

    fun flip() =
        when (this) {
            RIGHT -> LEFT
            LEFT -> RIGHT
            UP -> DOWN
            DOWN -> UP
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
                else -> throw Exception("unknown direction $ch")
            }

        fun setYDown() {
            DOWN.y = 1
            UP.y = -1
        }
    }
}

data class PointDouble(val x: Double, val y: Double) {
    operator fun plus(other: PointDouble) = PointDouble(x + other.x, y + other.y)
    operator fun plus(other: Point) = PointDouble(x + other.x, y + other.y)
    operator fun plus(dir: Direction) = PointDouble(x + dir.x, y + dir.y)

    operator fun minus(other: PointDouble) = PointDouble(x - other.x, y - other.y)
    operator fun unaryMinus() = PointDouble(-x, -y)

    operator fun times(factor: Double) = PointDouble(x * factor, y * factor)

    fun manhattan(other: PointDouble) = abs(x - other.x) + abs(y - other.y)
    fun dst(other: PointDouble) = sqrt((x - other.x) * (x - other.x) + (y - other.y) * (y - other.y))
    fun len() = dst(zero)
    fun norm() = times(1 / len())
    fun dot(other: PointDouble) = x * other.x + y * other.y
    fun cross(other: PointDouble) = x * y - y * x

    fun isPerpendicular(other: PointDouble) = abs(dot(other)) < 0.000001
    fun hasSameDirection(other: PointDouble) = dot(other) > 0.0
    fun hasOppositeDirection(other: PointDouble) = dot(other) < 0.0

    companion object {
        val zero = PointDouble(0.0, 0.0)
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
    fun withinBounds(minX: Int, maxX: Int, minY: Int, maxY: Int) =
        x in minX..maxX && y in minY..maxY

    fun neighbors() = Direction.values().map { this + it }
    fun neighbors(bounds: Point) = Direction.values().map { this + it }
        .filter { it.withinBounds(bounds) }

    fun neighbors9() = (-1..1)
        .flatMap { x ->
            (-1..1)
                .map { y -> Point(x, y) }
        }
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

const val fullBlock = '█'
