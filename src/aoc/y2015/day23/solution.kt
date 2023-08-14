package aoc.y2015.day23

import gears.puzzle

private fun main() {
    puzzle { turing(linesFrom("input.txt")) }
    puzzle { turing(linesFrom("input.txt"), 1) }
}

private fun turing(program: List<String>, a: Int = 0): Int? {
    val regs = mutableMapOf<String, Int>().apply { put("a", a) }
    var ip = 0
    while (ip in program.indices) {
        val (cmd, op1, op2) = program[ip].replace(",", "").split(" ").plus("-")
        when (cmd) {
            "inc" -> regs.merge(op1, 1, Int::plus).also { ip++ }
            "tpl" -> regs.merge(op1, 3, Int::times).also { ip++ }
            "hlf" -> regs.merge(op1, 2, Int::div).also { ip++ }
            "jmp" -> ip += op1.toInt()
            "jie" -> if (regs.getOrDefault(op1, 0) % 2 == 0) ip += op2.toInt() else ip++
            "jio" -> if (regs.getOrDefault(op1, 0) == 1) ip += op2.toInt() else ip++
        }
    }
    return regs["b"]
}

