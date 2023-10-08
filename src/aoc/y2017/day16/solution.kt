package aoc.y2017.day16

import gears.puzzle

private fun main() {
    puzzle { permutation(input().trim().split(",")) }
}

private fun permutation(input: List<String>): Any {
    var programs = ('a'..'p').toMutableList()
    for (move in input) {
        val op = move.first()
        val (b, c) = move.drop(1).split("/").plus("999").let { (bb, cc) ->
            if (op == 'p') programs.indexOf(bb.first()) to programs.indexOf(cc.first()) else bb.toInt() to cc.toInt()
        }
        when (op) {
            's' -> programs = programs.drop(programs.size - b).plus(programs.dropLast(b)).toMutableList()
            else -> {
                val q = programs[b]
                programs[b] = programs[c]
                programs[c] = q
            }
        }
    }
    return programs.joinToString("")
}
