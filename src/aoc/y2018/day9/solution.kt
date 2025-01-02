package aoc.y2018.day9

import gears.findInts
import gears.puzzle
import java.util.*

fun main() {
    puzzle { `Marble Mania`(input()) }
}

private fun `Marble Mania`(input: String): Any {
    val (players, points) = input.findInts()
    return game(players, points) to game(players, points * 100)
}

private fun game(players: Int, points: Int): Long {
    val board = Board().apply { addFirst(0) }
    val scores = LongArray(players)

    for (marble in 1..points) {
        if (marble % 23 == 0) {
            board.rotate(-7)
            scores[marble % players] += board.pop().toLong() + marble
        } else {
            board.rotate(2)
            board.addLast(marble)
        }
    }
    return scores.max()
}

private class Board : ArrayDeque<Int>() {
    fun rotate(amount: Int) {
        if (amount >= 0) {
            repeat(amount) { addFirst(removeLast()) }
        } else {
            repeat(-amount - 1) { addLast(remove()) }
        }
    }
}