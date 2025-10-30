package everydobycodes.y2024.q1

import gears.puzzle

fun main() {
    puzzle { TheBattleForTheFarmlands(input(), 1) }
    puzzle { TheBattleForTheFarmlands(input("input2.txt"), 2) }
    puzzle { TheBattleForTheFarmlands(input("input3.txt"), 3) }
}

private val map = mapOf('A' to 0, 'B' to 1, 'C' to 3, 'D' to 5)
private fun TheBattleForTheFarmlands(input: String, size: Int) = input
    .windowed(size, size)
    .map { s -> s.filter { it in map.keys } }
    .sumOf { s -> s.sumOf { (map[it] ?: 0) + s.length - 1 } }
