package aoc.y2017.day7

import gears.puzzle

private fun main() {
    puzzle { circus(inputLines("input.txt")) }
    puzzle { circus(inputLines("input.txt"), true) }
}

private fun circus(input: List<String>, part2: Boolean = false): Any {
    val all = input.map { line ->
        val (name, a) = line.split(" (")
        val (age, b) = a.split(")")
        val others = if (b.contains("->")) b.split(" -> ").last().split(", ") else listOf()
        Node(name, age.toInt(), others)
    }
    val lookup = all.associateBy { it.name }
    val root = all.map { it.name }.toMutableSet().also { all.forEach { e -> it.removeAll(e.others.toSet()) } }.first()

    if (!part2) return root

    updatedWeights(lookup[root]!!, lookup)

    for (p in lookup.values) {
        val weights = p.others.map { lookup[it] }.groupBy { it!!.weight }
        if (weights.size > 1) {
            val correctWeight = weights.filterValues { it.size > 1 }.keys.toList()[0]
            val wrongWeight = weights.filterValues { it.size == 1 }.keys.toList()[0]
            val diff = correctWeight - wrongWeight
            return (weights[wrongWeight]!![0]?.oWeight ?: 0) + if (correctWeight > wrongWeight) -diff else diff
        }
    }
    return 0
}

private fun updatedWeights(n: Node, lookup: Map<String, Node>) {
    for (support in n.others) {
        updatedWeights(lookup[support]!!, lookup)
        lookup[n.name]!!.weight += lookup[support]!!.weight
    }
}

private data class Node(val name: String, val oWeight: Int, val others: List<String> = listOf(), var weight: Int = oWeight)

