package aoc.y2023.day25

import gears.puzzle

private fun main() {
    puzzle("1") { snowverload(inputLines()) }
}

/**
 * https://en.wikipedia.org/wiki/Karger%27s_algorithm
 *
 * Create a graph.
 * While the graph has more than two nodes in it, pick a random node.
 * Pick one of the nodes that the first random node connects to.
 * Merge them together by removing the random nodes from the graph and adding a single new node to replace them.
 * Store a count of how many nodes were removed from the graph and replaced with this single node. Note that as we remove nodes that are combinations of other nodes, this number will grow to be the cumulative number of nodes removed.
 * Add all of the connections to the previously removed nodes to the new node.
 */
private fun snowverload(input: List<String>): Any {
    while (true) {
        val graph = parseGraph(input)
        val counts = graph.keys.associateWith { 1 }.toMutableMap()

        while (graph.size > 2) {
            val a = graph.keys.random()
            val b = graph.getValue(a).random()
            val newNode = "$a-$b"

            counts[newNode] = (counts.remove(a) ?: 0) + (counts.remove(b) ?: 0)
            graph.combine(a, b, newNode)
            graph.merge(a, newNode)
            graph.merge(b, newNode)
        }

        val (nodeA, nodeB) = graph.keys.toList()
        if (graph.getValue(nodeA).size == 3) return counts.getValue(nodeA) * counts.getValue(nodeB)
    }
}

private fun MutableMap<String, MutableList<String>>.combine(a: String, b: String, newNode: String) {
    this[newNode] = getValue(a).plus(getValue(b)).filter { it != a && it != b }.toMutableList()
}

private fun MutableMap<String, MutableList<String>>.merge(oldNode: String, newNode: String) {
    remove(oldNode)?.forEach { target -> getValue(target).replaceAll { if (it == oldNode) newNode else it } }
}

private fun parseGraph(input: List<String>) = buildMap<String, MutableList<String>> {
    input.map { it.split(": ") }.forEach { (s, t) ->
        t.split(" ").forEach { target ->
            getOrPut(s) { mutableListOf() }.add(target)
            getOrPut(target) { mutableListOf() }.add(s)
        }
    }
}.toMutableMap()