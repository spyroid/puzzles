package aoc.y2022.day8

import gears.Grid2
import gears.puzzle

fun main() {
    puzzle("t1") { part1(inputLines("test.txt")) }
    puzzle("1") { part1(inputLines("input.txt")) }
    puzzle("t2") { part2(inputLines("test.txt")) }
    puzzle("2") { part2(inputLines("input.txt")) }
}

private fun part1(input: List<String>): Int {
    fun markTrees(t: List<Tree>) {
        t.fold(-1) { h, tree ->
            if (tree.height > h) tree.height.also { tree.box += 1 } else h
        }
    }

    val forest = makeForest(input, 0)
    sequenceOf(forest, forest.rotate2D()).forEach {
        it.data().forEach { row ->
            markTrees(row).also { markTrees(row.reversed()) }
        }
    }
    return forest.data().sumOf { row -> row.count { it.box > 0 } }
}

private fun part2(input: List<String>): Int {
    fun scoreOf(height: Int, row: List<Tree>): Int {
        val score = row.indexOfFirst { it.height >= height } + 1
        return if (score == 0) row.size else score
    }

    fun markTrees(row: List<Tree>) = row.forEachIndexed { idx, tree ->
        tree.box = tree.box * scoreOf(tree.height, row.drop(idx + 1))
    }

    val forest = makeForest(input, 1)
    sequenceOf(forest, forest.rotate2D()).forEach {
        it.data().forEach { row ->
            markTrees(row).also { markTrees(row.reversed()) }
        }
    }
    return forest.data().maxOf { row -> row.maxOf { it.box } }
}

data class Tree(val height: Int, var box: Int)

private fun makeForest(input: List<String>, boxInit: Int): Grid2<Tree> {
    return Grid2.of(input) { c -> Tree(c.digitToInt(), boxInit) }
}

