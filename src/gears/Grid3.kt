package gears

class Grid3<T>(private val data: Array<Array<Entry<T>>>, val width: Int, val height: Int) {
    data class Entry<T>(val p: Point, var v: T)

    val maxX = width - 1
    val maxY = height - 1

    companion object {
        fun <T> of(input: List<String>, mapper: (Char) -> T): Grid3<T> {
            val (w, h) = input.first().length to input.size
            return Array(h) { y -> Array(w) { x -> Entry(Point(x, y), mapper(input[y][x])) } }
                .let { Grid3(it, w, h) }
        }

        fun <T> of(width: Int, height: Int, v: T) = Array(height) { y -> Array(width) { x -> Entry(Point(x, y), v) } }
            .let { Grid3(it, width, height) }
    }

    override fun toString() = data.joinToString("\n") { it.map { it.v }.joinToString("") }

    operator fun set(p: Point, value: T) = set(p.x, p.y, value)
    operator fun set(x: Int, y: Int, value: T) = isValid(x, y).also { if (it) data[y][x].v = value }
    operator fun get(p: Point) = get(p.x, p.y)
    operator fun get(x: Int, y: Int) = if (isValid(x, y)) this.data[y][x] else null
    fun isValid(p: Point) = isValid(p.x, p.y)
    fun isValid(x: Int, y: Int) = x >= 0 && x < width && y >= 0 && y < height

    fun all() = sequence<Entry<T>> { for (y in 0..maxY) for (x in 0..maxX) yield(data[y][x]) }
    fun around4(p: Point) = p.around4().mapNotNull { get(it) }
    fun around8(p: Point) = p.around8().mapNotNull { get(it) }

    fun <R> clone(transformer: Grid3<T>.(e: Entry<T>) -> R) = Array(height) { y -> Array(width) { x -> Entry(Point(x, y), transformer(data[y][x])) } }
        .let { Grid3(it, width, height) }
}

fun Grid3<Int>.inc(p: Point, amount: Int = 1) {
    get(p)?.let { it.v = it.v + amount }
}

const val fullBlock3 = '█'
