package aoc.y2025.day8

import gears.Point3D
import gears.puzzle

fun main() {
    puzzle {
        playground(inputLines())
    }
}

private fun playground(input: List<String>): Any {

    val points = input.map { Point3D.of(it) }

    val dist = (0..points.lastIndex).flatMap { i ->
        (i + 1..points.lastIndex).map { j ->
            points[i].distance(points[j]) to (i to j)
        }
    }.sortedBy { it.first }

    var root = IntArray(points.size) { it }
    var size = IntArray(points.size) { 1 }

    fun findRoot(x: Int): Int {
        if (root[x] != x) root[x] = findRoot(root[x])
        return root[x]
    }

    fun merge(a: Int, b: Int): Boolean {
        val ra = findRoot(a)
        val rb = findRoot(b)
        if (ra == rb) return false

        if (size[ra] >= size[rb]) {
            root[rb] = ra
            size[ra] += size[rb]
        } else {
            root[ra] = rb
            size[rb] += size[ra]
        }
        return true
    }

    dist.take(1000).forEach { (_, p) -> merge(p.first, p.second) }

    val part1 = root.indices.map { findRoot(it) }.toSet()
        .map { r -> size[r] }.sortedDescending()
        .take(3).fold(1) { acc, v -> acc * v }

    root = IntArray(points.size) { it }
    size = IntArray(points.size) { 1 }

    val part2 = dist.runningFold(Pair(points.size, 0L)) { clusters, (_, p) ->
        val nc = if (merge(p.first, p.second)) clusters.first - 1 else clusters.first
        Pair(nc, points[p.first].x * points[p.second].x.toLong())
    }.first { it.first == 1 }.second

    return part1 to part2
}
