package aoc.y2021.day04

import gears.puzzle

fun main() {
    puzzle { part1(inputLines("input.txt")) }
    puzzle { part2(inputLines("input.txt")) }
}

private data class Cell(var value: Int = 0, var marked: Boolean = false)

private class Board(data: List<String>) {

    val cells: Array<Array<Cell>> = Array(5) { Array(5) { Cell() } }

    init {
        data.forEachIndexed { idx1, line ->
            line.trim().split("\\s+".toRegex()).forEachIndexed { idx2, v ->
                cells[idx1][idx2].value = v.toInt()
            }
        }
    }

    val countCol = IntArray(5) { 0 };
    var score = 0

    fun markAndGetScore(marker: Int): Int {
        if (score != 0) return score
        var sumOfUnmarked = 0
        var fullRow = false
        cells.forEachIndexed { _, cells2 ->
            val countRow = cells2.onEachIndexed { col, cell ->
                if (cell.value == marker) {
                    cell.marked = true
                    countCol[col]++
                }
                if (!cell.marked) sumOfUnmarked += cell.value
            }.count { it.marked }
            if (countRow == 5) fullRow = true
        }

        if (fullRow || countCol.any { it == 5 }) score = marker * sumOfUnmarked
        return score
    }
}

private fun getInput(seq: List<String>) = seq[0].split(",").map { it.toInt() }.toList()

private fun getBoards(seq: List<String>) = seq.asSequence().drop(2).windowed(5, 6).map { Board(it) }.toList()

private fun part1(seq: List<String>): Int {
    val randomInput = getInput(seq)
    val boards = getBoards(seq)
    return randomInput.map { marker -> boards.maxOf { it.markAndGetScore(marker) } }.first() { it > 0 }
}

private fun part2(seq: List<String>): Int {
    val boards = getBoards(seq)

    return getInput(seq).asSequence().map { marker ->
        boards.filter { it.score == 0 }.map { it.markAndGetScore(marker) }.minOf { it }
    }.first { it > 0 }
}
