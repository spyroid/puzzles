package aoc.y2023.day22

import gears.puzzle
import kotlin.math.max
import kotlin.math.min

private fun main() {
    puzzle("1") { sandSlabs(inputLines()) }
    puzzle("2") { sandSlabs(inputLines(), true) }
}

private fun sandSlabs(input: List<String>, part2: Boolean = false): Any {

    fun findDependents(blocks: List<Block>, removed: Block) = buildSet {
        for (block in blocks) {
            val isUnstable = block != removed && block.z.first != 1 && blocks.none { other ->
                other != removed && other.id !in this && other.isBelow(block)
            }
            if (isUnstable) add(block.id)
        }
    }.size

    val blocks = input.mapIndexed { i, line ->
        val (a, b) = line.split('~').map { c -> c.split(',').map { s -> s.toInt() } }
        Block(i, a[0]..b[0], a[1]..b[1], a[2]..b[2])
    }.sortedBy { it.z.first }

    generateSequence(1) {
        blocks.count { block ->
            (block.z.first != 1 && blocks.none { other -> other.isBelow(block) }).also {
                if (it) block.moveDown()
            }
        }
    }.takeWhile { it != 0 }.count()

    val above = blocks.associate { it.id to blocks.filter { other -> other != it && other.isAbove(it) } }
    val below = blocks.associate { it.id to blocks.filter { other -> other != it && other.isBelow(it) } }

    return when {
        !part2 -> blocks.count { block -> !above[block.id].orEmpty().any { below[it.id].orEmpty().size == 1 } }
        else -> blocks.fold(0) { acc, removed -> acc + findDependents(blocks, removed) }
    }
}

private data class Block(val id: Int, var x: IntRange, var y: IntRange, var z: IntRange) {
    fun isAbove(other: Block) = z.first == (other.z.last + 1) && intersectWith(other)
    fun isBelow(other: Block) = z.last == (other.z.first - 1) && intersectWith(other)
    fun moveDown() = IntRange(z.first - 1, z.last -1).also { z = it }
    private fun intersectWith(other: Block) = max(x.first, other.x.first) <= min(x.last, other.x.last) && max(y.first, other.y.first) <= min(y.last, other.y.last)
}
