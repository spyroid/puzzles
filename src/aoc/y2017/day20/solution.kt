package aoc.y2017.day20

import gears.puzzle
import kotlin.math.absoluteValue

private fun main() {
    puzzle { swarn(inputLines().asParticles()) }
    puzzle { swarn2(inputLines().asParticles()) }
}

private fun swarn(particles: List<Particle>) = (1..500).fold(particles) { next, _ -> next.onEach { it.update() } }.minBy { it.dist() }.id
private fun swarn2(particles: List<Particle>) = (1..500).fold(particles) { next, _ ->
    next.onEach { it.update() }.groupBy { it.pos }.filterValues { it.size == 1 }.flatMap { it.value }
}.size

private data class Particle(val id: Int, val pos: Coors3D, val velocity: Coors3D, val acc: Coors3D) {
    fun update() {
        velocity.plus(acc); pos.plus(velocity)
    }

    fun dist() = pos.x.absoluteValue + pos.y.absoluteValue + pos.z.absoluteValue
}

private data class Coors3D(var x: Int, var y: Int, var z: Int) {
    fun plus(other: Coors3D) {
        x += other.x; y += other.y; z += other.z
    }
}

private fun List<String>.asParticles() = mapNotNull { s -> "[-0-9]+".toRegex().findAll(s).map { it.value.toInt() }.toList() }
    .mapIndexed { i, v -> Particle(i, Coors3D(v[0], v[1], v[2]), Coors3D(v[3], v[4], v[5]), Coors3D(v[6], v[7], v[8])) }
