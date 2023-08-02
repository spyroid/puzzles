package nik

import gears.puzzle

fun main() {
    puzzle { solution("011100") }
    puzzle { solution("111") }
    puzzle { solution("1111010101111") }
}

// Div by 2 is SHR 1
private fun solution(s: String): Int {
    var steps = 0
    val line = s.dropWhile { it == '0' }.toByteArray()
    var idx = line.lastIndex
    while (idx >= 0) {
        if (line[idx] == '1'.code.toByte()) line[idx] = '0'.code.toByte() else idx -= 1
        steps += 1
    }
    return steps - 1
}
