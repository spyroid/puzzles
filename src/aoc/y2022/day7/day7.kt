package aoc.y2022.day7

import puzzle

fun main() {
    puzzle("t1") { part1(buildFs(linesFrom("test.txt"))) }
    puzzle("1") { part1(buildFs(linesFrom("input.txt"))) }
    puzzle("t2") { part2(buildFs(linesFrom("test.txt"))) }
    puzzle("2") { part2(buildFs(linesFrom("input.txt"))) }
}

private fun part1(fs: Folder): Long {
    return fs.flatten().filter { it.size < 100_000 }.sumOf { it.size }
}

private fun part2(fs: Folder): Long {
    return fs.flatten().sortedBy { it.size }.first { 70_000_000 - fs.size + it.size > 30_000_000 }.size
}

private data class Folder(val name: String, val parent: Folder?, var size: Long = 0) {
    val items: MutableList<Folder> = mutableListOf()

    fun addFile(size: Long) {
        var f: Folder? = this
        while (f != null) {
            f.size = f.size + size
            f = f.parent
        }
    }

    fun flatten(root: Folder = this): List<Folder> = root.items.flatMap { flatten(it) } + root
}

private fun buildFs(input: List<String>): Folder {
    val root = Folder("/", null)
    var current = root

    var i = 1
    while (i < input.size) {
        val line = input[i]
        if (line.startsWith("$ ls")) {
            while (i + 1 < input.size && !input[i + 1].startsWith("$")) {
                val l = input[++i].split(" ")
                if (l[0] != "dir") current.addFile(l[0].toLong())
            }
        } else if (line.startsWith("$ cd")) {
            val fn = line.drop(5)
            if (fn == "..") {
                current = current.parent ?: root
            } else {
                var f = Folder(fn, current)
                current.items.add(f)
                current = f
            }
        }
        i += 1
    }
    return root
}
