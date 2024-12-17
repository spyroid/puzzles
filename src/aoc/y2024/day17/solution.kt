package aoc.y2024.day17

import gears.puzzle

fun main() {
    puzzle { chronospatialComputer(input()) }
}

private fun chronospatialComputer(input: String): Any {
    val regs = input.substringBefore("\n\n").let { r -> r.lines().map { it.substringAfterLast(" ").toLong() } }
    val program = input.substringAfter("m: ").split(',').map { it.toInt() }

    fun run(regs: List<Long>): List<Int> {
        var ip = 0
        var (ax, bx, cx) = regs
        fun read() = program.getOrNull(ip++)
        fun Int.toCombo() = when (this) {
            4 -> ax.toInt() and 7
            5 -> bx.toInt() and 7
            6 -> cx.toInt() and 7
            else -> this
        }

        val out = mutableListOf<Int>()
        while (ip in program.indices) {
            val (cmd, op) = read() to read()
            if (op == null) break
            when (cmd) {
                0 -> ax = ax / 1.shl(op.toCombo().toInt())
                1 -> bx = bx xor op.toLong()
                2 -> bx = op.toCombo().toLong()
                3 -> if (ax != 0L) ip = op.toInt()
                4 -> bx = bx xor cx
                5 -> out.add(op.toCombo())
                6 -> bx = ax / 1.shl(op.toCombo().toInt())
                7 -> cx = ax / 1.shl(op.toCombo().toInt())
            }
        }
        return out
    }

    val p1 = run(regs)

    fun matchBits(ax: Long, output: Int) = buildList {
        for (bits in 0L..7L) {
            val nax = (ax shl 3) or bits
            val isMatch = run(listOf(nax, regs[1], regs[2])).firstOrNull() == output
            if (isMatch) add(nax)
        }
    }
    val p2 = program.reversed().fold(listOf(0L)) { candidates, inst -> candidates.flatMap { matchBits(it, inst) } }.min()

    return p1.joinToString(",") to p2
}
