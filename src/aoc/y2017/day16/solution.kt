package aoc.y2017.day16

import gears.puzzle

private fun main() {
    puzzle { permutation(input().trim().split(","), 1) }
    puzzle { permutation(input().trim().split(","), 1_000_000_000) }
}

private fun permutation(input: List<String>, times: Int): Any {
    var programs = ('a'..'p').toMutableList()
    val seen = mutableListOf<String>()

    repeat(times) {
        if (seen.contains(programs.joinToString(""))) {
            return seen[times % seen.size]
        } else {
            seen.add(programs.joinToString(""))
        }
        for (move in input) {
            val op = move.first()
            val (b, c) = move.drop(1).split("/").plus("999").let { (bb, cc) ->
                if (op == 'p') programs.indexOf(bb.first()) to programs.indexOf(cc.first()) else bb.toInt() to cc.toInt()
            }
            when (op) {
                's' -> programs = programs.drop(programs.size - b).plus(programs.dropLast(b)).toMutableList()
                else -> programs[b] = programs[c].also { programs[c] = programs[b] }
            }
        }
    }
    return programs.joinToString("")
}
