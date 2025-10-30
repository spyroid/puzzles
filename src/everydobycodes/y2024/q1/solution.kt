package everydobycodes.y2024.q1

import gears.puzzle

fun main() {
    puzzle {
        TheBattleForTheFarmlands(input())
    }
    puzzle {
        TheBattleForTheFarmlands2(input("input2.txt"))
    }
}

private fun TheBattleForTheFarmlands(input: String) = calculate(input)

private fun TheBattleForTheFarmlands2(input: String) = input
    .windowed(2, 2)
    .sumOf { calculate(it) + (if (it.contains('x')) 0 else 2) }

private fun calculate(s: String) = s.map {
    when (it) {
        'B' -> 1
        'C' -> 3
        'D' -> 5
        else -> 0
    }
}.sum()