package other

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sign

fun main() {
    val players = mutableListOf(Player(Point(0, 0), Direction.UP), Player(Point(5, 0), Direction.LEFT))
    val arena = Arena(players, 0)
    arena.play()
    println(arena.chain)
    println(arena.steps)
}

private fun <T> MutableList<T>.rotateLeft(n: Int) = drop(n % size) + take(n % size)

private data class Point(val x: Int, val y: Int)

private data class Player(val pos: Point, var dir: Direction) {
    fun chebyshevDistance(p1: Point, p2: Point, dir: Direction): Int {
        val dx = p2.x - p1.x
        val dy = p2.y - p1.y

        if ((dir.x * dir.y != 0 && dx != dy) || dx.sign != dir.x || dy.sign != dir.y) return -1
        return max(abs(dx), abs(dy))
    }

    fun findClosestPlayer(otherPlayers: List<Player>): Pair<Int, Player>? {
        val next = dir.listFrom()
            .mapIndexed { idx, dir ->
                val p = otherPlayers.map { p -> Pair(chebyshevDistance(pos, p.pos, dir), p) }
                    .filter { it.first != -1 }
                    .minByOrNull { it.first }?.second
                if (p == null) null else Pair(idx + 1, p)
            }.firstOrNull { it != null }
        return next
    }
}

private data class Arena(val players: MutableList<Player>, val start: Int) {
    val chain = mutableListOf<Player>()
    var steps = 0

    init {
        chain.add(players[start]).also { players.removeAt(start) }
    }

    fun play() {
        while (players.isNotEmpty()) {
            val p = chain.last().findClosestPlayer(players)
            if (p != null) chain.add(p.second).also { steps += p.first } else break
        }
    }
}

private enum class Direction(val x: Int, val y: Int) {
    UP(0, 1),
    UP_RIGHT(1, 1),
    RIGHT(1, 0),
    DOWN_RIGHT(1, -1),
    DOWN(0, -1),
    DOWN_LEFT(-1, -1),
    LEFT(-1, 0),
    UP_LEFT(-1, 1);

    fun listFrom(): List<Direction> = values().toMutableList().rotateLeft(ordinal + 1)
}

