package codingame.what_the_brainfuck

import gears.puzzle

fun main() {
    puzzle {
        solve(inputLines())
    }
}

private fun solve(inputLines: List<String>): Any {
    val (L, S, N) = inputLines.first().split(" ").map { it.toInt() }
    val mem = IntArray(S).toMutableList()
    val params = ArrayDeque<Int>(inputLines.drop(1 + L).take(N).map { it.toInt() })
    var (ip, dp) = 0 to 0
    val jumps = mutableMapOf<Int, Int>()
    val stack = ArrayDeque<Int>()
    val program = inputLines.drop(1).take(L).joinToString("").filter { it in "<>+-,.[]" }
        .onEachIndexed { i, c ->
            when (c) {
                '[' -> stack.addFirst(i).also { jumps[i] = -1 }
                ']' -> if (stack.isNotEmpty()) stack.removeFirst().also { jumps[i] = it; jumps[it] = i }
            }
        }.also { if (jumps.containsValue(-1) || (jumps.isEmpty() && it.contains(']'))) return "SYNTAX ERROR" }

    while (ip in program.indices) {
        when (program[ip++]) {
            '+' -> mem[dp] = mem[dp] + 1
            '-' -> mem[dp] = mem[dp] - 1
            '.' -> print(mem[dp].toChar())
            ',' -> mem[dp] = params.removeFirst()
            '>' -> dp++
            '<' -> dp--
            '[' -> if (mem[dp] == 0) jumps[ip - 1]?.also { ip = it + 1 }
            ']' -> if (mem[dp] != 0) jumps[ip - 1]?.also { ip = it + 1 }
        }
        if (dp !in 0..mem.lastIndex) return "POINTER OUT OF BOUNDS"
        if (mem[dp] !in 0..255) return "INCORRECT VALUE"
    }
    return ""
}