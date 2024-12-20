package gears

class Grid<T> private constructor(private var grid: MutableList<MutableList<T>>) {
    operator fun set(p: Point, value: T): Boolean {
        return set(p.x, p.y, value)
    }

    operator fun set(x: Int, y: Int, value: T): Boolean {
        val valid = isValid(x, y)
        if (valid) grid[y][x] = value
        return valid
    }

    operator fun get(p: Point) = this.grid[p.y][p.x]

    operator fun get(x: Int, y: Int) = this.grid[y][x]

    fun data() = grid

    fun deepHashCode() = data().toTypedArray().contentDeepHashCode()

    companion object {
        fun <T> of(input: List<String>, mapper: (Char) -> T) = Grid(input.map { row -> row.map(mapper).toMutableList() }.toMutableList())
        fun <T> of(cols: Int, rows: Int, v: T) = Grid(Array(rows) { mutableListOf<T>().apply { repeat(cols) { add(v) } } }.toMutableList())
    }

    fun rotate2D(): Grid<T> {
        val grid = MutableList(this.grid[0].size) { i -> MutableList(this.grid.size) { j -> this.grid[j][i] } }
        return Grid(grid)
    }

    fun edgeAsNumber(edge: List<T>, mapper: (T) -> Char) = edge.map(mapper).joinToString("").toLong(2)
    fun topEdge() = data().first()
    fun bottomEdge() = data().last()
    fun leftEdge() = MutableList(data().size) { y -> data()[y].first() }
    fun rightEdge() = MutableList(data().size) { y -> data()[y].last() }

    fun flipY(): Grid<T> {
        val grid = MutableList(this.grid.size) { i -> this.grid[i].reversed().toMutableList() }
        return Grid(grid)
    }

    fun flipX(): Grid<T> {
        val grid = this.grid.reversed().toMutableList()
        return Grid(grid)
    }

    fun at(x: Int, y: Int): T? = if (isValid(x, y)) this.grid[y][x] else null
    fun at(p: Point): T? = at(p.x, p.y)
    fun entryAt(p: Point): Entry<T>? = at(p)?.let { Entry(p, it) }
    fun entryAt(x: Int, y: Int): Entry<T>? = entryAt(Point(x, y))
    fun isValid(p: Point) = isValid(p.x, p.y)
    fun isValid(x: Int, y: Int) = x >= 0 && x < width && y >= 0 && y < height

    fun pointsAround4(p: Point) = p.around4().filter { isValid(it) }

    fun pointsOf(body: (x: Int, y: Int, v: T) -> Boolean): List<Point> {
        val res = mutableListOf<Point>()
        for (y in this.grid.indices) {
            for (x in this.grid[y].indices) {
                if (body(x, y, this.grid[y][x])) res.add(Point(x, y))
            }
        }
        return res
    }


    fun clone(transformer: Grid<T>.(x: Int, y: Int, e: T) -> T): Grid<T> {
        val cloned = Grid(MutableList(data().size) { data()[it].toMutableList() })
        for (y in data().indices) {
            for (x in data()[y].indices) cloned[x, y] = transformer(x, y, this[x, y])
        }
        return cloned
    }

    override fun toString() = buildString { for (line in data()) appendLine(line.joinToString("")) }

    fun allPoints() = sequence { for (y in 0..maxY) for (x in 0..maxX) yield(Point(x, y)) }
    fun all() = sequence<Entry<T>> { for (y in 0..maxY) for (x in 0..maxX) yield(Entry(Point(x, y), at(x, y)!!)) }

    fun around4(p: Point): List<Entry<T>> = p.around4().mapNotNull { p -> at(p)?.let { Entry(p, it) } }
    fun around8(p: Point): List<Entry<T>> = p.around8().mapNotNull { p -> at(p)?.let { Entry(p, it) } }
    fun around8(x: Int, y: Int) = around8(Point(x, y))

    data class Entry<T>(val p: Point, val v: T)

    val maxX = data().first().lastIndex
    val maxY = data().lastIndex
    val width = data().first().size
    val height = data().size

    fun rotateColDown(col: Int, n: Int) {
        repeat(n) {
            val v = at(col, maxY) ?: throw RuntimeException()
            for (y in maxY downTo 1) this[col, y] = this[col, y - 1]
            this[col, 0] = v
        }
    }

    fun rotateColUp(col: Int, n: Int) {
        repeat(n) {
            val v = at(col, 0) ?: throw RuntimeException()
            for (y in 1..maxY) this[col, y - 1] = this[col, y]
            this[col, maxY] = v
        }
    }

    fun rotateRowRight(row: Int, n: Int) {
        repeat(n) {
            val v = at(maxX, row) ?: throw RuntimeException()
            for (x in maxX downTo 1) this[x, row] = this[x - 1, row]
            this[0, row] = v
        }
    }

    fun rotateRowLeft(row: Int, n: Int) {
        repeat(n) {
            val v = at(0, row) ?: throw RuntimeException()
            for (x in 1..maxX) this[x - 1, row] = this[x, row]
            this[maxX, row] = v
        }
    }

    fun move(p: Point, dir: Direction): Point {
        this[p] = this[p + dir].also { this[p + dir] = this[p] }
        return p + dir
    }

}

fun Grid<Int>.inc(p: Point, amount: Int = 1) {
    this[p.x, p.y] = this[p.x, p.y] + amount
}

fun Grid<Long>.inc(p: Point, amount: Long = 1L) {
    this[p.x, p.y] = this[p.x, p.y] + amount
}

const val fullBlock = '█'
