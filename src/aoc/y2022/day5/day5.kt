package aoc.y2022.day5

import puzzle

fun main() {
    puzzle { craneMove(readLinesFrom("test.txt"), testCrates.toMutableList(), 5, true) }
    puzzle { craneMove(readLinesFrom("input.txt"), crates.toMutableList(), 10, true) }

    puzzle { craneMove(readLinesFrom("test.txt"), testCrates.toMutableList(), 5, false) }
    puzzle { craneMove(readLinesFrom("input.txt"), crates.toMutableList(), 10, false) }
}

val testCrates = listOf("ZN", "MCD", "P")
val crates = listOf("CZNBMWQV", "HZRWCB", "FQRJ", "ZSWHFNMT", "GFWLNQP", "LPW", "VBDRGCQJ", "ZQNBW", "HLFCGTJ")

private fun craneMove(input: List<String>, crates: MutableList<String>, skip: Int, reversed: Boolean): String {
    return input
        .drop(skip)
        .onEach { line ->
            val (amount, from, to) = line.split("move ", " from ", " to ").let {
                listOf(it[1].toInt(), it[2].toInt() - 1, it[3].toInt() - 1)
            }
            val toMove = crates[from].takeLast(amount).let { if (reversed) it.reversed() else it }
            crates[to] = crates[to].plus(toMove)
            crates[from] = crates[from].dropLast(amount)
        }
        .count()
        .let { crates.map { it.last() }.joinToString("") }
}
