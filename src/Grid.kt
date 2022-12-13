class Grid<T> {
    operator fun set(c: Cell, value: T) {
        this.grid[c.y][c.x] = value
    }

    operator fun get(c: Cell): T {
        return this.grid[c.y][c.x]
    }

    data class Cell(val x: Int, val y: Int)

    private var grid: MutableList<MutableList<T>>

    companion object {
        fun <T> of(input: List<String>, mapper: (Char) -> T): Grid<T> {
            return Grid(input.map { row -> row.map(mapper).toMutableList() }.toMutableList())
        }
    }

    private constructor(grid: MutableList<MutableList<T>>) {
        this.grid = grid
    }
}
