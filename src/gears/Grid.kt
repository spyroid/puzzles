package gears

class Grid<T>(val data: Array<Array<Entry<T>>>) {
    data class Entry<T>(val p: Point, var v: T)

    val width = data.firstOrNull()?.size ?: 0
    val height = data.size
    val maxX = width - 1
    val maxY = height - 1

    companion object {
        fun <T> of(input: List<String>, mapper: (Char) -> T) = (input.first().length to input.size).let { (w, h) ->
            Grid(Array(h) { y -> Array(w) { x -> Entry(Point(x, y), mapper(input[y][x])) } })
        }

        fun <T> of(width: Int, height: Int, v: T) = Grid(Array(height) { y -> Array(width) { x -> Entry(Point(x, y), v) } })
    }

    override fun toString() = data.joinToString("\n") { it.map { it.v }.joinToString("") }

    operator fun set(p: Point, value: T) = set(p.x, p.y, value)
    operator fun set(x: Int, y: Int, value: T) = isValid(x, y).also { if (it) data[y][x].v = value }
    operator fun get(p: Point) = get(p.x, p.y)
    operator fun get(x: Int, y: Int) = if (isValid(x, y)) this.data[y][x] else null
    fun isValid(p: Point) = isValid(p.x, p.y)
    fun isValid(x: Int, y: Int) = x in 0..<width && y in 0..<height

    fun all() = sequence<Entry<T>> { for (y in 0..maxY) for (x in 0..maxX) yield(data[y][x]) }
    fun around4(p: Point) = p.around4().mapNotNull { get(it) }
    fun around8(p: Point) = p.around8().mapNotNull { get(it) }

    fun rotateClockwise() = Array(width) { i -> Array(height) { j -> Entry(Point(j, i), this.data[maxY - j][i].v) } }.let { Grid(it) }
    fun flipX() = Array(height) { y -> Array(width) { x -> Entry(Point(x, y), this.data[maxY - y][x].v) } }.let { Grid(it) }
    fun <R> clone(transformer: Grid<T>.(e: Entry<T>) -> R) = Array(height) { y -> Array(width) { x -> Entry(Point(x, y), transformer(data[y][x])) } }
        .let { Grid(it) }

    @Suppress("UNCHECKED_CAST")
    fun inc(p: Point, amount: Int = 1) {
        val v = get(p)?.v ?: return
        when (v) {
            is Int -> set(p, v.plus(amount) as T)
            is Long -> set(p, v.plus(amount) as T)
            is Char -> set(p, v.plus(amount) as T)
        }
    }

}