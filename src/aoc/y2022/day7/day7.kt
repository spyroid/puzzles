package aoc.y2022.day7

import puzzle

fun main() {
    puzzle("t1") { part1(linesFrom("test.txt")) }
    puzzle("1") { part1(linesFrom("input.txt")) }
    puzzle("t2") { part2(linesFrom("test.txt")) }
    puzzle("2") { part2(linesFrom("input.txt")) }
}

private fun part1(input: List<String>): Long {
    return buildFs(input).flatten().filter { it.size < 100_000 }.sumOf { it.size }
}

private fun part2(input: List<String>): Long {
    return buildFs(input).let { fs ->
        fs.flatten().sortedBy { it.size }.first { 70_000_000 - fs.size + it.size > 30_000_000 }.size
    }
}

private data class Folder(val name: String, val parent: Folder?, var size: Long = 0) {
    val folders = mutableListOf<Folder>()

    fun addFile(size: Long) {
        generateSequence(this) { f -> f.parent.also { f.size += size } }.count()
    }

    fun flatten(root: Folder = this): List<Folder> = root.folders.flatMap { flatten(it) } + root
}

private fun buildFs(input: List<String>): Folder {
    val root = Folder("/", null)
    var current = root

    input.drop(1)
        .forEach { s ->
            val (a, b, c) = s.split(" ").plus("")
            if (a == "$") {
                if (b == "cd") {
                    current = if (c == "..") {
                        current.parent ?: root
                    } else {
                        Folder(c, current).also { current.folders.add(it) }
                    }
                }
            } else if (a != "dir") current.addFile(a.toLong())
        }
    return root
}
