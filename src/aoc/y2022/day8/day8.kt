package aoc.y2022.day8

import puzzle
import rotate2D

fun main() {
    puzzle("t1") { part1(linesFrom("test.txt")) }
    puzzle("1") { part1(linesFrom("input.txt")) }
    puzzle("t2") { part2(linesFrom("test.txt")) }
    puzzle("2") { part2(linesFrom("input.txt")) }
}

private fun part1(input: List<String>): Int {
    fun markTrees(t: List<Tree>) {
        t.fold(-1) { h, tree -> if (tree.height > h) tree.height.also { tree.seen += 1 } else h }
    }

    var forest = makeForest(input, 0)
    sequenceOf(forest, forest.rotate2D()).forEach {
        it.forEach { trees -> markTrees(trees).also { markTrees(trees.reversed()) } }
    }
    return forest.sumOf { row -> row.count { it.seen > 0 } }
}

private fun part2(input: List<String>): Int {
    fun scoreOf(height: Int, row: List<Tree>): Int {
        val score = row.indexOfFirst { it.height >= height } + 1
        return if (score == 0) row.size else score
    }

    fun markTrees(row: List<Tree>) {
        row.forEachIndexed { idx, tree -> tree.seen = tree.seen * scoreOf(tree.height, row.drop(idx + 1)) }
    }

    var forest = makeForest(input, 1)
    sequenceOf(forest, forest.rotate2D()).forEach {
        it.forEach { row -> markTrees(row).also { markTrees(row.reversed()) } }
    }
    return forest.maxOf { row -> row.maxOf { it.seen } }
}

data class Tree(val height: Int, val x: Int, val y: Int, var seen: Int)

private fun makeForest(input: List<String>, seenVal: Int): List<List<Tree>> {
    return List(input.size) { row -> List(input[row].length) { i -> Tree(input[row][i].digitToInt(), row, i, seenVal) } }
}

