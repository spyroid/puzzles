package aoc.y2016.day14

import gears.md5
import gears.puzzle
import gears.toHex

private fun main() {
    puzzle { pad("jlmsuwbz", 1) }
    puzzle { pad("jlmsuwbz", 2017) }
}

private fun pad(input: String, times: Int): Int {
    val cache = mutableMapOf<Int, String>()
    fun md5x(i: Int) = cache.computeIfAbsent(i) { _ -> (1..times).fold("$input$i") { acc, _ -> md5(acc).toHex() } }
    val keys = mutableListOf<String>()
    for (i in 0..Int.MAX_VALUE) {
        val hex = md5x(i)
        hex.windowed(3).firstOrNull { it[0] == it[1] && it[1] == it[2] }?.also {
            val fifths = it.plus(it).drop(1)
            for (j in (i + 1)..(i + 1000)) {
                if (md5x(j).contains(fifths)) {
                    keys.add(hex)
                    break
                }
            }
        }
        if (keys.size == 64) return i
    }
    return 0
}
