package aoc.y2018.day8

import gears.findInts
import gears.puzzle

private fun main() {
    puzzle { memoryManeuver(inputLines().first().findInts()) }
}

private fun memoryManeuver(nums: List<Int>): Any {
    fun parse(data: List<Int>): Node {
        var payload = data.drop(2)
        return Node().apply {
            children = IntRange(0, data.first() - 1).map { parse(payload).also { payload = payload.drop(it.len()) } }
            meta = payload.take(data[1])
        }
    }
    return parse(nums).let { it.metas() to it.value() }
}

private data class Node(var meta: List<Int> = listOf(), var children: List<Node> = listOf()) {
    fun len(): Int = children.sumOf { it.len() } + meta.size + 2
    fun metas(): Int = children.sumOf { it.metas() } + meta.sum()
    fun value(): Int = if (children.isEmpty()) meta.sum() else meta.map { children.getOrNull(it - 1)?.value() ?: 0 }.sum()
}
