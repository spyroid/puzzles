package aoc.y2022.day10

import puzzle

fun main() {
    puzzle("test 1") { crt(linesFrom("test.txt")) }
    puzzle("1") { crt(linesFrom("input.txt")) }
}

private fun crt(input: List<String>): Int {
    var x = 1
    return sequence {
        input.forEach {
            it.split(" ").plus("").let { (op, v) ->
                if (op == "noop") yield(0) else yieldAll(listOf(0, v.toInt()))
            }
        }
    }.mapIndexed { i, v ->
        var cycle = i + 1
        val strength = if (cycle == 20 || (cycle - 20) % 40 == 0) {
            x * cycle
        } else 0

        var pos = i % 40
        if (pos == 0) println()
        if (pos + 1 in x..x + 2) print("#") else print(".")

        strength.also { x += v }
    }.sum().also { println() }
}
