package leetcode

import puzzle

fun main() {
    puzzle { romanToInt("III") }
    puzzle { romanToInt("LVIII") }
    puzzle { romanToInt("MCMXCIV") }
}

val map = mapOf(
    'I' to Pair(1, setOf('V', 'X')),
    'V' to Pair(5, emptySet()),
    'X' to Pair(10, setOf('L', 'C')),
    'L' to Pair(50, emptySet()),
    'C' to Pair(100, setOf('D', 'M')),
    'D' to Pair(500, emptySet()),
    'M' to Pair(1000, emptySet())
)

fun romanToInt(s: String): Int {
    return s.asSequence().plusElement(' ')
        .zipWithNext { a, b ->
            val e = map[a] ?: Pair(0, emptySet())
            if (b in e.second) -e.first else e.first
        }.sum()
}
