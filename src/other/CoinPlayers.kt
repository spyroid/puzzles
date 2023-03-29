package other

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sign

fun main() {
    val players = listOf(
        Player(1, Point(-10, -10)),
        Player(2, Point(-10, 10)),
        Player(3, Point(0, -10)),
        Player(4, Point(0, 10)),
        Player(5, Point(10, -10)),
        Player(6, Point(10, 10)),
        Player(7, Point(-9, -10)),
        Player(8, Point(-9, 0)),
    )
    Arena(players, 5, Direction.NW).play().also { println("Steps: ${it.steps} Players: ${it.chain}") }

    val players2 = listOf(
        Player(1, Point(-1000000, -1000000)),
        Player(2, Point(-1000000, 1000000)),
        Player(3, Point(0, -1000000)),
        Player(4, Point(0, 1000000)),
        Player(5, Point(1000000, -1000000)),
        Player(6, Point(1000000, 1000000)),
        Player(7, Point(-999999, -1000000)),
        Player(8, Point(-999999, 0)),
    )
    Arena(players2, 4, Direction.SE).play().also { println("Steps: ${it.steps} Players: ${it.chain}") }
}

private fun <T> MutableList<T>.rotateLeft(n: Int) = drop(n % size) + take(n % size)

private data class Point(val x: Int, val y: Int)

private data class Player(val id: Int, val pos: Point) {
    fun chebyshevDistance(p1: Point, p2: Point, dir: Direction): Int {
        val dx = p2.x - p1.x
        val dy = p2.y - p1.y
        val isDiag = dir.x * dir.y != 0

        if (isDiag) {
            if (dx.sign != dir.x || dy.sign != dir.y || abs(dx) != abs(dy)) return -1
        } else {
            if ((p1.x != p2.x && p1.y != p2.y) || dx.sign != dir.x || dy.sign != dir.y) return -1
        }
        return max(abs(dx), abs(dy))
    }

    fun findNextPlayer(otherPlayers: List<Player>, dir: Direction): NextPlayer? {
        return dir.listFrom().mapIndexed { idx, dir ->
            val p = otherPlayers.map { p -> Pair(chebyshevDistance(pos, p.pos, dir), p) }
                .filter { it.first != -1 }
                .minByOrNull { it.first }?.second
            if (p == null) null else NextPlayer(p, idx + 1, dir)
        }.firstOrNull { it != null }
    }
}

private data class NextPlayer(val player: Player, val steps: Int, val dir: Direction)

private data class Arena(val players: List<Player>, val startId: Int, var dir: Direction) {
    val chain = mutableListOf<Player>()
    var steps = 0
    var all: MutableList<Player> = players.toMutableList()

    init {
        val p = all.firstOrNull { it.id == startId } ?: throw Error("No such player $startId")
        chain.add(p).also { all.remove(p) }
    }

    fun play(): Arena {
        while (all.isNotEmpty()) {
            val next = chain.last().findNextPlayer(all, dir)
            if (next != null) chain.add(next.player).also {
                steps += next.steps
                dir = next.dir.opposite()
                all.remove(next.player)
            } else break
        }
        return this
    }
}

private enum class Direction(val x: Int, val y: Int) {
    N(0, 1),
    NE(1, 1),
    E(1, 0),
    SE(1, -1),
    S(0, -1),
    SW(-1, -1),
    W(-1, 0),
    NW(-1, 1);

    fun listFrom(): List<Direction> = values().toMutableList().rotateLeft(ordinal + 1)
    fun opposite() = values()[(ordinal + 4) % values().size]
}
