package aoc.y2023.day7

import gears.puzzle

private fun main() {
    puzzle("1") { camelCards(inputLines(), false) }
    puzzle("2") { camelCards(inputLines(), true) }
}

private fun camelCards(input: List<String>, part2: Boolean): Any {
    val cardsMap = mutableMapOf('T' to 10, 'J' to 11, 'Q' to 12, 'K' to 13, 'A' to 14)
        .apply { if (part2) this['J'] = -1 }
    return input.asSequence()
        .map { s ->
            val (hand, bid) = s.split(" ")
            val remapped = hand.map { cardsMap[it] ?: (it - '0') }
            val filtered = remapped.filter { it != -1 }
            val groups = filtered.groupingBy { it }.eachCount()
            val type = when {
                groups.size <= 1 -> 7
                groups.values.any { it == filtered.size - 1 } -> 6
                groups.size == 2 -> 5
                groups.values.any { it == filtered.size - 2 } -> 4
                groups.size == 3 -> 3
                groups.size == 4 -> 2
                else -> 1
            }
            Hand(remapped, type, bid.toInt())
        }
        .sortedWith(comparator)
        .withIndex()
        .sumOf { (it.index + 1) * it.value.bid }
}

private data class Hand(val cards: List<Int>, val type: Int, val bid: Int)

private val comparator = compareBy<Hand> { it.type }.thenBy { it.cards[0] }.thenBy { it.cards[1] }
    .thenBy { it.cards[2] }.thenBy { it.cards[3] }.thenBy { it.cards[4] }
