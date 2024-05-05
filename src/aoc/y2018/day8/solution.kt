package aoc.y2018.day8

import gears.findInts
import gears.puzzle

private fun main() {
    puzzle { memoryManeuver(inputLines().first().findInts()) }
}

private fun memoryManeuver(nums: List<Int>): Any {

    fun parse(data: List<Int>): Node {
        val (children, meta) = data
        var payload = data.drop(2)
        val node = Node()
        repeat(children) {
            val n = parse(payload)
            node.children.add(n)
            payload = payload.drop(n.len())
        }
        node.meta = payload.take(meta)
        return node
    }

    val n = parse(nums)
    println(n)
    return n.metas()
}

private data class Node(var meta: List<Int> = listOf(), var children: MutableList<Node> = mutableListOf()) {
    fun len(): Int = children.sumOf { it.len() } + meta.size + 2
    fun metas(): Int = children.sumOf { it.metas() } + meta.sum()
}

