package aoc.y2024.day23

import gears.puzzle

fun main() {
    puzzle { LANParty(inputLines()) }.also { }
}

private fun LANParty(input: List<String>): Any {

    val all = buildMap<String, MutableSet<String>> {
        input.map { it.split('-') }.forEach { (a, b) -> getOrPut(a, { mutableSetOf() }).add(b);getOrPut(b, { mutableSetOf() }).add(a) }
    }

    // https://en.wikipedia.org/wiki/Clique_problem
    fun cliques(limit: Int): Set<List<String>> = buildSet {
        fun find(node: String, clique: Set<String>) {
            val c = clique.sorted()
            if (c in this) return
            add(c)
            if (c.size >= limit) return

            for (other in all.getValue(node)) {
                if (other in clique || !all.getValue(other).containsAll(clique)) continue
                find(other, clique + other)
            }
        }
        all.keys.forEach { find(it, setOf(it)) }
    }

    val part1 = cliques(3).filter { it.size == 3 }.count { it.any { node -> node.startsWith('t') } }
    val part2 = cliques(99).maxBy { it.size }.joinToString(",")

    return part1 to part2
}
