package aoc.y2018.day9

import gears.findInts
import gears.puzzle
import gears.rotate

fun main() {
    puzzle { `Marble Mania`(input()) }
}

private fun `Marble Mania`(input: String): Any {
    val (players, points) = input.findInts()
    return game(players, points) to game(players, points * 100)
}

private fun game(players: Int, points: Int): Long {
    val board = ArrayDeque<Int>(players).apply { addFirst(0) }
    val scores = LongArray(players)

    for (marble in 1..points) {
        if (marble % 23 == 0) {
            board.rotate(-7)
            scores[marble % players] += board.removeFirst().toLong() + marble
        } else {
            board.rotate(2)
            board.addLast(marble)
        }
    }
    return scores.max()
}
