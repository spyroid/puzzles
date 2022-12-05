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

private data class Move(val amount: Int, val from: Int, val to: Int)

private fun craneMove(input: List<String>, crates: MutableList<String>, skip: Int, reversed: Boolean): String {
    return input.asSequence().drop(skip)
        .map { line -> line.split("move ", " from ", " to ").let {
                Move(it[1].toInt(), it[2].toInt() - 1, it[3].toInt() - 1)
            }
        }
        .onEach { m ->
            val cons = crates[m.from].takeLast(m.amount)
            crates[m.from] = crates[m.from].dropLast(m.amount)
            crates[m.to] = crates[m.to].plus(if (reversed) cons.reversed() else cons)
        }
        .count().let { crates.map { it.last() }.joinToString("") }
}
