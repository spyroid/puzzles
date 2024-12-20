package gears

class Grid2<T> private constructor(private val dataMap: Map<Point, Entry<T>>, val width: Int, val height: Int) {
    data class Entry<T>(val p: Point, var v: T)

    companion object {
        fun <T> of(input: List<String>, mapper: (Char) -> T = { it as T }): Grid2<T> = buildMap {
            for (y in input.indices) for (x in input.first().indices) {
                val p = Point(x, y)
                val e = Entry(p, mapper(input[y][x]))
                put(p, e)
            }
        }.let { Grid2(it, input.first().length, input.size) }
    }

    fun all() = dataMap.values
    fun around4(point: Point) = point.borderManhattan(1).mapNotNull { dataMap[it] }
    operator fun get(point: Point) = dataMap[point]

}