package aoc.y2021.day19

import gears.puzzle
import kotlin.math.abs

fun main() {
    puzzle { part1(allFrom("input.txt")) }
    puzzle { part2(allFrom("input.txt")) }
}

private fun part1(input: String) = Scanners(input).assembleMap().beacons.size

private fun part2(input: String) = Scanners(input).assembleMap().scannersPositions.let { positions ->
    positions.flatMapIndexed { index, first -> positions.drop(index + 1).map { second -> first to second } }
        .maxOf { (first, second) -> first distanceTo second }
}

private class Scanners(input: String) {

    private val scanners = input
        .split("\n\n")
        .filter { it.isNotEmpty() }
        .map { s ->
            Scanner(
                s.trim().lines().drop(1)
                    .map { it.split(",") }
                    .map { (x, y, z) -> Point3D(x.toInt(), y.toInt(), z.toInt()) }
                    .toSet()
            )
        }

    fun assembleMap(): AssembledMap {
        val foundBeacons = scanners.first().beacons.toMutableSet()
        val foundScannersPositions = mutableSetOf(Point3D(0, 0, 0))

        val remaining = ArrayDeque<Scanner>().apply { addAll(scanners.drop(1)) }
        while (remaining.isNotEmpty()) {
            val candidate = remaining.removeFirst()
            when (val transformedCandidate = Scanner(foundBeacons).getTransformedIfOverlap(candidate)) {
                null -> remaining.add(candidate)
                else -> {
                    foundBeacons.addAll(transformedCandidate.beacons)
                    foundScannersPositions.add(transformedCandidate.position)
                }
            }
        }

        return AssembledMap(foundBeacons, foundScannersPositions)
    }

    private data class Scanner(val beacons: Set<Point3D>) {
        fun allRotations() = beacons.map { it.allRotations() }.transpose().map { Scanner(it) }

        fun getTransformedIfOverlap(otherScanner: Scanner): TransformedScanner? {
            return otherScanner.allRotations().firstNotNullOfOrNull { otherReoriented ->
                beacons.firstNotNullOfOrNull { first ->
                    otherReoriented.beacons.firstNotNullOfOrNull { second ->
                        val otherPosition = first - second
                        val otherTransformed = otherReoriented.beacons.map { otherPosition + it }.toSet()
                        when ((otherTransformed intersect beacons).size >= 12) {
                            true -> TransformedScanner(otherTransformed, otherPosition)
                            false -> null
                        }
                    }
                }
            }
        }

        private fun List<Set<Point3D>>.transpose(): List<Set<Point3D>> {
            return when (all { it.isNotEmpty() }) {
                true -> listOf(map { it.first() }.toSet()) + map { it.drop(1).toSet() }.transpose()
                false -> emptyList()
            }
        }
    }

    private data class TransformedScanner(val beacons: Set<Point3D>, val position: Point3D)

    data class AssembledMap(val beacons: Set<Point3D>, val scannersPositions: Set<Point3D>)

    data class Point3D(val x: Int, val y: Int, val z: Int) {
        operator fun plus(other: Point3D) = Point3D(x + other.x, y + other.y, z + other.z)

        operator fun minus(other: Point3D) = Point3D(x - other.x, y - other.y, z - other.z)

        infix fun distanceTo(other: Point3D) = abs(x - other.x) + abs(y - other.y) + abs(z - other.z)

        fun allRotations(): Set<Point3D> {
            return setOf(
                Point3D(x, y, z), Point3D(x, -z, y), Point3D(x, -y, -z), Point3D(x, z, -y), Point3D(-x, -y, z),
                Point3D(-x, -z, -y), Point3D(-x, y, -z), Point3D(-x, z, y), Point3D(-z, x, -y), Point3D(y, x, -z),
                Point3D(z, x, y), Point3D(-y, x, z), Point3D(z, -x, -y), Point3D(y, -x, z), Point3D(-z, -x, y),
                Point3D(-y, -x, -z), Point3D(-y, -z, x), Point3D(z, -y, x), Point3D(y, z, x), Point3D(-z, y, x),
                Point3D(z, y, -x), Point3D(-y, z, -x), Point3D(-z, -y, -x), Point3D(y, -z, -x),
            )
        }
    }
}
