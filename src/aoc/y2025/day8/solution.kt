package aoc.y2025.day8

import gears.Point3D
import gears.puzzle
import kotlin.collections.indices
import kotlin.collections.map

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

    var du = DisjointUnion(points)
    dist.take(1000).forEach { (_, p) -> du.merge(p.first, p.second) }
    val part1 = du.clustersSizes().sortedDescending().take(3).fold(1) { acc, v -> acc * v }

    du = DisjointUnion(points)
    val part2 = dist.runningFold(Pair(points.size, 0L)) { clusters, (_, p) ->
        val nc = if (du.merge(p.first, p.second)) clusters.first - 1 else clusters.first
        Pair(nc, points[p.first].x * points[p.second].x.toLong())
    }.first { it.first == 1 }.second

    return part1 to part2
}

private class DisjointUnion(points: List<Point3D>) {
    val root = IntArray(points.size) { it }
    val size = IntArray(points.size) { 1 }

    fun clustersRoots() = root.indices.map { findRoot(it) }.toSet()
    fun clustersSizes() = clustersRoots().map { r -> size[r] }

    fun findRoot(x: Int): Int {
        if (root[x] != x) root[x] = findRoot(root[x])
        return root[x]
    }

    fun merge(a: Int, b: Int): Boolean {
        val (ra, rb) = findRoot(a) to findRoot(b)
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
}