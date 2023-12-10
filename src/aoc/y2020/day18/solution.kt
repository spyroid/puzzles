package aoc.y2020.day18

import gears.puzzle

private fun main() {
    puzzle("test") {
        part1(inputLines("test.txt"))
    }
    puzzle {
        part1(inputLines("input.txt"))
    }
    puzzle("test") {
        part2(inputLines("test.txt"))
    }
    puzzle {
        part2(inputLines("input.txt"))
    }
}

private fun part2(lines: List<String>): Long {
    return lines
        .asSequence()
        .map { it.replace(" ", "") }
        .filter { it.isNotEmpty() }
        .map { eval(it, true) }
        .sum()
}

private fun part1(lines: List<String>): Long {
    return lines
        .asSequence()
        .map { it.replace(" ", "") }
        .filter { it.isNotEmpty() }
        .map { eval(it, false) }
        .sum()
}

private fun eval(line: String, flag: Boolean): Long {
    var s = line
    while (s.contains("(")) {
        val a = s.indexOfLast { it == '(' }
        val b = s.substring(a).indexOfFirst { it == ')' }
        val sub = s.substring(a + 1, b + a)
        val v = eval(sub, flag)
        s = s.substring(0, a) + v + s.substring(b + a + 1, s.lastIndex + 1)
    }
    val seq = sequence {
        var x = ""
        s.forEach {
            if (x.isEmpty() || it in '0'..'9') {
                x += it
            } else {
                yield(x)
                yield(it.toString())
                x = ""
            }
        }
        yield(x)
    }.toMutableList()

    if (flag) {
        while (seq.indexOf("+") >= 0) {
            val i = seq.indexOf("+")
            seq.add(i - 1, (seq[i - 1].toLong() + seq[i + 1].toLong()).toString())
            repeat(3) { seq.removeAt(i)}
        }
    }
    var res = seq.first().toLong()
    seq.drop(1).zipWithNext().forEach { (op, v) ->
        when (op) {
            "*" -> res *= v.toLong()
            "+" -> res += v.toLong()
        }
    }
    return res

}

