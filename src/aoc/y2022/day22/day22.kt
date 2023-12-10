package aoc.y2022.day22

import gears.puzzle

fun main() {
    puzzle("t1") { part1(inputLines("test.txt")) }
    puzzle("1") { part1(inputLines("input.txt")) }
    puzzle("t2") { part2(inputLines("test.txt")) }
    puzzle("2") { part2(inputLines("input.txt")) }
}

private fun part1(lines: List<String>): Int {
    return workTheGame(lines, false)
}

private fun part2(lines: List<String>): Int {
    return workTheGame(lines, true)
}

private fun splitStringToCommands(mess: String): List<Pair<Command, Int>> {
    val result = mutableListOf<Pair<Command, Int>>()
    var currentCounter = 0
    mess.forEach {
        if (it.isDigit()) {
            currentCounter = currentCounter * 10 + it.digitToInt()
        } else {
            if (currentCounter != 0) {
                result.add(Command.FORWARD to currentCounter)
            }
            currentCounter = 0
            result.add((if (it == 'R') Command.RIGHT else Command.LEFT) to 1)
        }
    }
    if (currentCounter != 0) {
        result.add(Command.FORWARD to currentCounter)
    }
    return result
}

private fun workTheGame(input: List<String>, onCube: Boolean): Int {
    val directions = input.takeLast(1)[0]
    val mapWithMover =
        if (!onCube) MapWithMoverPlain(input.takeWhile { it.isNotBlank() }) else CubicMap(input.takeWhile { it.isNotBlank() })
    val commands = splitStringToCommands(directions)
    for ((command, amount) in commands) {
        repeat(amount) {
            when (command) {
                Command.FORWARD -> mapWithMover.move()
                else -> mapWithMover.rotateSelf(command == Command.RIGHT)
            }
        }
    }
    return mapWithMover.getPassword()
}


private enum class CubeSides { TOP, DOWN, RIGHT, LEFT, FRONT, BACK }
private enum class Moves(val coords: Pair<Int, Int>, val ind: Int) {
    RIGHT(0 to 1, 0), DOWN(1 to 0, 1), LEFT(0 to -1, 2), UP(-1 to 0, 3)
}
//    L.---.
//    E.TOP.
//    F.---.
//    T FRONT

private abstract class MapWithMover(val map: List<String>) {
    var dir: Moves = Moves.RIGHT
    val n: Int = map.size
    val ms: List<Int> = map.map { it.length }
    val begins = map.map { it.indexOfFirst { it2 -> it2 != ' ' } }
    var x: Int = 0
    var y: Int = begins[0]

    abstract fun move()
    fun getPassword(): Int {
        return (x + 1) * 1000 + (y + 1) * 4 + dir.ind
    }

    fun rotateSelf(clockwise: Boolean) {
        dir = rotate(dir, clockwise)
    }

    fun isOutside(nx: Int, ny: Int) = (nx < 0) || (nx >= n) || (ny < 0) || (ny >= ms[nx]) || (map[nx][ny] == ' ')

    companion object {
        val rotations = listOf(Moves.RIGHT, Moves.DOWN, Moves.LEFT, Moves.UP)
        fun rotate(toDir: Moves, clockwise: Boolean): Moves {
            return if (clockwise) rotations[(toDir.ind + 1) % 4] else rotations[(toDir.ind + 3) % 4]
        }
    }
}

private class CubicMap(map: List<String>) : MapWithMover(map) {
    val cubeEdge: Int = ms.zip(begins).minOf { it.first - it.second }
    val sides: MutableMap<CubeSides, Pair<IntRange, IntRange>> = mutableMapOf()
    val distances: MutableMap<CubeSides, Int> = mutableMapOf()
    val relativeDistances: MutableMap<Pair<CubeSides, CubeSides>, Int> = mutableMapOf()

    init {
        fun dfs(currentSide: CubeSides, currentPos: Pair<Int, Int>, dist: Int) {
            sides[currentSide] = ((currentPos.first until (currentPos.first + cubeEdge)) to
                    (currentPos.second until (currentPos.second + cubeEdge)))
            distances[currentSide] = dist
            for (dir in rotations) {
                val nPos = currentPos + dir.coords * cubeEdge
                val fixedDir = fixedDir(currentSide, dir, cubeEdge)
                val nextSide = whichSideNext[currentSide to fixedDir]!! // This 'function' is total
                if (!isOutside(nPos.first, nPos.second) && nextSide !in sides) {
                    dfs(nextSide, nPos, dist + 1)
                }
            }
        }

        fun distOnlyDfs(currentSide: CubeSides, currentPos: Pair<Int, Int>, dist: Int, distTo: CubeSides, used: MutableSet<CubeSides>) {
            used.add(currentSide)
            relativeDistances[distTo to currentSide] = dist
            for (dir in rotations) {
                val nPos = currentPos + dir.coords * cubeEdge
                val fixedDir = fixedDir(currentSide, dir, cubeEdge)
                val nextSide = whichSideNext[currentSide to fixedDir]!! // This 'function' is total
                if (!isOutside(nPos.first, nPos.second) && nextSide !in used) {
                    distOnlyDfs(nextSide, nPos, dist + 1, distTo, used)
                }
            }
        }

        dfs(CubeSides.TOP, 0 to begins[0], 0)
        assert(sides.size == 6)
        for (side1 in sides.keys) {
            distOnlyDfs(side1, sides[side1]!!.first.first to sides[side1]!!.second.first, 0, side1, mutableSetOf())
        }
    }

    override fun move() {
        val (nx, ny) = (x to y) + dir.coords
        if (!isOutside(nx, ny)) {
            if (map[nx][ny] == '.') {
                x = nx
                y = ny
            }
            return
        }
        val ourSide = sides.filter { x in it.value.first && y in it.value.second }.keys.first()
        val (relX, relY) = (x to y) - (sides[ourSide]!!.first.first to sides[ourSide]!!.second.first)
        val ourFixedDir = fixedDir(ourSide, dir, cubeEdge)
        val nextSide = whichSideNext[ourSide to ourFixedDir]!!
        var nextDir = dir
        var (fixX, fixY) = relX to relY
        repeat(relativeDistances[ourSide to nextSide]!! - 1) {
            nextDir = rotate(nextDir, cubeEdge == 4) // I HATE IT
            val temp = fixX
            fixX = fixY
            fixY = cubeEdge - 1 - temp
        }
        when (nextDir) {
            Moves.UP -> {
                fixX = 3
            }

            Moves.RIGHT -> {
                fixY = 0
            }

            Moves.DOWN -> {
                fixX = 0
            }

            Moves.LEFT -> {
                fixY = 3
            }
        }
        val fx = sides[nextSide]!!.first.first + fixX
        val fy = sides[nextSide]!!.second.first + fixY
        if (map[fx][fy] == '#') return
        x = fx
        y = fy
        dir = nextDir
    }

    companion object {
        val orderPerimeter = listOf(CubeSides.FRONT, CubeSides.RIGHT, CubeSides.BACK, CubeSides.LEFT, CubeSides.FRONT)
        val whichSideNext = buildMap(12 * 2) {
            for (i in 0..3) {
                put(orderPerimeter[i] to Moves.RIGHT, orderPerimeter[i + 1])
                put(orderPerimeter[i + 1] to Moves.LEFT, orderPerimeter[i])
                put(CubeSides.TOP to rotations[(3 - i + 2) % 4], orderPerimeter[i])
                put(orderPerimeter[i] to Moves.UP, CubeSides.TOP)
                put(orderPerimeter[i] to Moves.DOWN, CubeSides.DOWN)
                put(
                    CubeSides.DOWN to if (rotations[(3 - i + 2) % 4] == Moves.UP || rotations[(3 - i + 2) % 4] == Moves.DOWN) rotate(
                        rotate(rotations[(3 - i + 2) % 4], false),
                        false
                    ) else rotations[(3 - i + 2) % 4], orderPerimeter[i]
                )
            }
        }
        val fixes = mapOf((4 to CubeSides.RIGHT) to 3, (50 to CubeSides.RIGHT) to 1, (50 to CubeSides.LEFT) to 1, (50 to CubeSides.BACK) to 1)
        fun fixedDir(plane: CubeSides, dir: Moves, cubeEdge: Int): Moves {
            if ((cubeEdge to plane) !in fixes) return dir
            var fixedDir = dir
            repeat(fixes[cubeEdge to plane]!!) {
                fixedDir = rotate(fixedDir, true)
            }
            return fixedDir
        }
    }
}

private class MapWithMoverPlain(map: List<String>) : MapWithMover(map) {
    override fun move() {
//        if (areWeOnCube) {
//            moveOnCube()
//        }
        var (nx, ny) = (x to y) + dir.coords
        if (dir.coords.first == -1) {
            if (nx < 0) {
                nx = n - 1
            }
        }
        if (dir.coords.first == 1) {
            if (nx >= n) {
                nx = 0
            }
        }
        if (dir.coords.second == -1) {
            if (ny < begins[nx]) {
                ny = ms[nx]
            }
        }
        if (dir.coords.second == 1) {
            if (ny >= ms[nx]) {
                ny = begins[nx]
            }
        }
        while (ny >= ms[nx] || ny < begins[nx] || map[nx][ny] == ' ') {
            nx = (nx + dir.coords.first + n) % n
            ny += dir.coords.second
        }
        if (map[nx][ny] == '#') return
        x = nx
        y = ny
    }
}

private enum class Command { FORWARD, RIGHT, LEFT }

private operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>): Pair<Int, Int> {
    return (this.first - other.first) to (this.second - other.second)
}

private operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
    return (this.first + other.first) to (this.second + other.second)
}

private operator fun Pair<Int, Int>.times(other: Int): Pair<Int, Int> {
    return (this.first * other) to (this.second * other)
}
