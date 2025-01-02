package aoc.y2018.day9

import gears.findInts
import gears.puzzle
import java.util.LinkedList

fun main() {
    puzzle { `Marble Mania`(input()) }
}

private fun `Marble Mania`(input: String): Any {
    val (players, points) = input.findInts()
    return solve(players, points) to solve(players, points * 100)
}

private fun solve(players: Int, points: Int): Long {
    var currMarble = 0L
    val playersScores = LongArray(players)
    val marbles: LinkedList<Long> = LinkedList(listOf(0L))
    var currPlayer = 0
    var iterator = marbles.listIterator(1)
    while (currMarble++ < points) {
        if (marbles.size == 1) {
            iterator.add(currMarble)
        } else if (currMarble % 23 == 0L) {
            (0..7).forEach { _ ->
                if (iterator.hasPrevious()) {
                    iterator.previous()
                } else {
                    iterator = marbles.listIterator(marbles.size - 1)
                }
            }
            val toRemove = iterator.next()
            iterator.remove()
            playersScores[currPlayer] += (currMarble + toRemove)
            iterator.next()
        } else if (!iterator.hasNext()) {
            iterator = marbles.listIterator(1)
            iterator.add(currMarble)
        } else {
            iterator.next()
            iterator.add(currMarble)
        }
        if (++currPlayer % players == 0) {
            currPlayer = 0
        }
    }
    return playersScores.max()
}

