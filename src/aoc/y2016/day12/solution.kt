package aoc.y2016.day12

import gears.puzzle

private fun main() {
    puzzle { leo(linesFrom("input.txt"), 0) }
    puzzle { leo(linesFrom("input.txt"), 1) }
}

private fun leo(lines: List<String>, c: Int): Int? {
    val regs = mutableMapOf("a" to 0, "b" to 0, "c" to c, "d" to 0)
    var ip = 0
    while (ip in lines.indices) {
        val op = lines[ip].split(" ")
        when (op[0]) {
            "cpy" -> regs[op[2]] = op[1].toIntOrNull() ?: regs[op[1]] ?: 0
            "inc" -> regs[op[1]] = (regs[op[1]] ?: 0) + 1
            "dec" -> regs[op[1]] = (regs[op[1]] ?: 0) - 1
            "jnz" -> if (regs[op[1]] != 0) ip += op[2].toInt() - 1
        }
        ip += 1
    }
    return regs["a"]
}
