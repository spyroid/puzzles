package aoc.y2024.day17

import gears.puzzle

fun main() {
    puzzle { chronospatialComputer(input()) }
}

private fun chronospatialComputer(input: String): Any {
    val regs = input.substringBefore("\n\n").let { r -> r.lines().map { it.substringAfterLast(" ").toLong() } }
    val program = input.substringAfter("m: ").split(',').map { it.toLong() }

    fun run(regs: List<Long>): MutableList<Long> {
        var ip = 0
        var (ax, bx, cx) = regs
        fun read() = program.getOrNull(ip++)
        fun Long.toCombo() = when (this) {
            4L -> ax and 7
            5L -> bx and 7
            6L -> cx and 7
            else -> this
        }

        val out = mutableListOf<Long>()
        while (ip in program.indices) {
            val (cmd, op) = read() to read()
            if (op == null) break
            when (cmd) {
                0L -> ax = ax / 1.shl(op.toCombo().toInt())
                1L -> bx = bx xor op
                2L -> bx = op.toCombo()
                3L -> if (ax != 0L) ip = op.toInt()
                4L -> bx = bx xor cx
                5L -> out.add(op.toCombo())
                6L -> bx = ax / 1.shl(op.toCombo().toInt())
                7L -> cx = ax / 1.shl(op.toCombo().toInt())
            }
        }
        return out
    }

    val p1 = run(regs)

    fun matchBits(ax: Long, output: Long) = buildList {
        for (bits in 0L..7L) {
            val nax = (ax shl 3) or bits
            val isMatch = run(listOf(nax, regs[1], regs[2])).firstOrNull() == output
            if (isMatch) add(nax)
        }
    }

    val p2 = program.reversed().fold(listOf(0L)) { candidates, inst -> candidates.flatMap { matchBits(it, inst) } }.min()

    return p1.joinToString(",") to p2
}
