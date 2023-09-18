package aoc.y2016.day19

import gears.puzzle
import kotlin.math.pow

private fun main() {
    puzzle { jo11(3014603) }
    puzzle { jo12(3014603) }
//    puzzle { pad("jlmsuwbz", 2017) }
}

// https://www.youtube.com/watch?v=uCsD3ZGzMgE
private fun jo11(input: Int) = input.toString(2).let { it.drop(1) + it[0] }.toInt(2)
private fun jo12(input: Int): Int = (1..input).fold(Pair(0, 1)) { v, i ->
    if (i.toDouble() == 2.0.pow(v.first)) Pair(v.first + 1, 1) else Pair(v.first, v.second + 2)
}.second
