package aoc.y2021.day21

import kotlin.system.measureTimeMillis

data class Die(var rolls: Int = 0) {
    private fun roll() = ++rolls % 100
    fun rolls(times: Int) = generateSequence { roll() }.take(times).sum()
}

data class Player(var space: Int, var score: Int = 0) {
    fun move(steps: Int) {
        space = (space + steps) % 10
        score += space + 1
    }
}

fun part1(s1: Int, s2: Int): Int {
    val die = Die()
    val p1 = Player(s1 - 1)
    val p2 = Player(s2 - 1)
    while (true) {
        p1.move(die.rolls(3))
        if (p1.score >= 1000) return p2.score * die.rolls
        p2.move(die.rolls(3))
        if (p2.score >= 1000) return p1.score * die.rolls
    }
}

fun part2(s1: Int, s2: Int): Long {
    val allRolls = mapOf(3 to 1L, 4 to 3L, 5 to 6L, 6 to 7L, 7 to 6L, 8 to 3L, 9 to 1L)

    fun play(pos: List<Int>, scores: List<Int>, turn: Int): Pair<Long, Long> {
        return allRolls.map { (roll, count) ->
            val newPos = pos.toMutableList()
            val newScores = scores.toMutableList()

            newPos[turn] = (pos[turn] + roll) % 10
            newScores[turn] = scores[turn] + newPos[turn] + 1

            if (newScores[turn] >= 21) {
                if (turn == 0) count to 0L else 0L to count
            } else {
                val nextResult = play(newPos, newScores, (turn + 1) % 2)
                nextResult.first * count to nextResult.second * count
            }
        }.reduce { acc, p -> acc.first + p.first to acc.second + p.second }
    }

    val result = play(listOf(s1 - 1, s2 - 1), listOf(0, 0), 0)
    return maxOf(result.first, result.second)
}

fun main() {
    check(part1(4, 8) == 739785)
    measureTimeMillis { print("⭐️ Part1: ${part1(7, 1)}") }.also { time -> println(" in $time ms") }

    check(part2(4, 8) == 444356092776315)
    measureTimeMillis { print("⭐️ Part2: ${part2(7, 1)}") }.also { time -> println(" in $time ms") }
}

