fun main() {
    minesweeper(listOf("XOOO", "OOOO", "XXOO"))
}

fun minesweeper(area: List<String>) {
    fun at(x: Int, y: Int): Boolean {
        return !(x < 0 || y < 0 || x >= area.first().length || y >= area.size || area[y][x] != 'X')
    }

    fun countXAt(x: Int, y : Int): Int {
        return listOf(at(x - 1, y - 1), at(x, y - 1), at(x + 1, y - 1),
            at(x - 1, y), at(x + 1, y),
            at(x - 1, y + 1), at(x, y + 1), at(x + 1, y + 1), ).count { it }
    }

    for (y in area.indices) {
        for (x in 0 until area.first().length) {
            val el = area[y][x]
            if (el != 'X') {
                print(countXAt(x, y))
            } else {
                print(el)
            }
        }
        println()
    }
}

