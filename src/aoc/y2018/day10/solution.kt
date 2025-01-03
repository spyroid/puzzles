package aoc.y2018.day10

import gears.*

private fun main() {
    puzzle { stars(inputLines().toParticles()) }
}

private fun stars(input: List<Particle>): Any {
    return generateSequence(Long.MAX_VALUE) { input.update().bounds().let { (p1, p2) -> (p2.x - p1.x) * (p2.y - p1.y).toLong() } }
        .zipWithNext()
        .takeWhile { it.first > it.second }
        .count()
        .also { println(input.update(true).toStringGrid()) }
}

private fun List<Particle>.update(back: Boolean = false) = this.onEach { it.update(back) }.map { it.pos }

private fun List<String>.toParticles() = map { s -> s.findInts().toList() }
    .map { v -> Particle(Point(v[0], v[1]), Point(v[2], v[3])) }

private data class Particle(var pos: Point, val velocity: Point) {
    fun update(back: Boolean = true) {
        if (back) pos -= velocity else pos += velocity
    }
}
