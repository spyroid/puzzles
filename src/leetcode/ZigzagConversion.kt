package leetcode

fun main() {
    println(ZigzagConversion().convert("0", 1))
}

class ZigzagConversion {

    fun convert(s: String, numRows: Int): String {
        var dy = 0
        var x = 0
        var y = 0

        val arr = Array(numRows) { Array(s.length / numRows) { ' ' } }

        for ((i, c) in s.withIndex()) {
            arr[y][x] = c
            if (i % (numRows - 1) == 0) dy = 1
            if (y == numRows - 1) dy = -1
            x += if (dy == 1) 0 else 1
            y += dy
        }

        return buildString {
            arr.forEach { row ->
                row.forEach { if (it != ' ') append(it) }
                append("\n")
            }
        }
    }

}
