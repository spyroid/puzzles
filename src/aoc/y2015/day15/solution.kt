package aoc.y2015.day15

import gears.puzzle

private fun main() {
    puzzle {
        part1(linesFrom("test.txt"))
    }
}

private fun part1(lines: List<String>): Int {
    val cap = listOf(-1, 2)
    val dur = listOf(-2, 3)
    val fl = listOf(6, -2)
    val tex = listOf(3, -1)

    var res = 0

    fun gen(total: Int): Sequence<List<Int>> {
        val a = ArrayDeque(IntArray(total).also { it[0] = 10 }.toList())
        return sequence {
            repeat(total) { yield(a).also { a.shift() } }
            var j = 1
            for (i in 9 downTo 5) {
                a[0] = i
                var ii = j++ % total
                if (ii == 0) ii = 1
                a[ii] = a[ii] + 1
                repeat(total) { yield(a).also { a.shift() } }
            }
        }
    }

    gen(3).onEach { println(it) }.toList()

    return res
}

private fun ArrayDeque<Int>.shift() {
    addFirst(removeLast())
}
