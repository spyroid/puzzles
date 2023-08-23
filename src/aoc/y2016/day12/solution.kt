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
        val parts = lines[ip].split(" ")
        when (parts[0]) {
            "cpy" -> regs[parts[2]] = parts[1].toIntOrNull() ?: regs[parts[1]] ?: 0
            "inc" -> regs[parts[1]] = (regs[parts[1]] ?: 0) + 1
            "dec" -> regs[parts[1]] = (regs[parts[1]] ?: 0) - 1
            "jnz" -> if (regs[parts[1]] != 0) ip += parts[2].toInt() - 1
        }
        ip += 1
    }
    return regs["a"]
}
