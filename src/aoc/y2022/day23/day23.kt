package aoc.y2022.day23

import gears.Point
import gears.puzzle

fun main() {
    puzzle { `Unstable Diffusion`(inputLines()) }
}

private fun `Unstable Diffusion`(input: List<String>): Any {

    val grove = Grove.from(input)
    fun groves() = generateSequence(grove, Grove::performRound)

    val part1 = groves().elementAt(10).emptyTiles()
    val part2 = groves().zipWithNext().takeWhile { it.first.elves != it.second.elves }.count() + 1

    return part1 to part2
}


private data class Grove(val elves: Set<Point>, val dirs: List<Dir> = listOf(Dir.NORTH, Dir.SOUTH, Dir.WEST, Dir.EAST)) {
    fun performRound(): Grove {
        val (nonMovers, movers) = elves.partition { elf ->
            dirs.none { dir -> dir.neighbours(elf).any(elves::contains) }
        }
        val propositions = movers.associateWith { elf ->
            dirs.firstOrNull { dir -> dir.neighbours(elf).none(elves::contains) }?.move?.invoke(elf) ?: elf
        }
        val validPropositions = propositions.values.groupingBy { it }.eachCount().filter { it.value == 1 }.keys
        val movedOrLeft = propositions.map { (current, next) -> if (next in validPropositions) next else current }

        return Grove(nonMovers union movedOrLeft, dirs.rotate())
    }

    fun emptyTiles(): Int {
        val min = Point(elves.minOf { it.x }, elves.minOf { it.y })
        val max = Point(elves.maxOf { it.x }, elves.maxOf { it.y })
        val xSize = max.x - min.x + 1
        val ySize = max.y - min.y + 1
        return (xSize * ySize) - elves.size
    }

    private fun <T> List<T>.rotate() = drop(1) + first()

    companion object {
        fun from(input: List<String>): Grove {
            val elves = input.flatMapIndexed { y, line -> line.mapIndexedNotNull { x, c -> if (c != '#') null else Point(x, y) } }.toSet()
            return Grove(elves)
        }
    }
}

private enum class Dir(val neighbours: (Point) -> Set<Point>, val move: (Point) -> Point) {
    NORTH(
        { (x, y) -> setOf(Point(x - 1, y - 1), Point(x, y - 1), Point(x + 1, y - 1)) },
        { (x, y) -> Point(x, y - 1) }
    ),
    SOUTH(
        { (x, y) -> setOf(Point(x - 1, y + 1), Point(x, y + 1), Point(x + 1, y + 1)) },
        { (x, y) -> Point(x, y + 1) }
    ),
    WEST(
        { (x, y) -> setOf(Point(x - 1, y - 1), Point(x - 1, y), Point(x - 1, y + 1)) },
        { (x, y) -> Point(x - 1, y) }
    ),
    EAST(
        { (x, y) -> setOf(Point(x + 1, y - 1), Point(x + 1, y), Point(x + 1, y + 1)) },
        { (x, y) -> Point(x + 1, y) }
    )
}