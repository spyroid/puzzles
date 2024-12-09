package aoc.y2024.day9

import gears.puzzle

fun main() {
    puzzle { diskFragmenter(input()) }
    puzzle { diskFragmenter2(input()) }
}

private fun diskFragmenter(input: String): Any {

    val blocks = input.map { it.digitToInt().toLong() }.toTypedArray()
    var (a, b) = 0 to blocks.lastIndex
    var (sum, multiplier) = 0L to 0
    var buffer = blocks[b]

    fun updateSum(v: Long) {
        sum += v * multiplier++
    }

    fun move(amount: Long) {
        var steps = 0
        while (steps < amount) {
            if (buffer-- > 0) {
                updateSum(b / 2L).also { steps++ }
            } else {
                b -= 2
                buffer = blocks[b]
            }
        }
    }

    while (a < b) {
        repeat(blocks[a].toInt()) { updateSum(a / 2L) }.also { a++ }
        move(blocks[a++])
    }
    repeat(buffer.toInt()) { updateSum(a / 2L) }

    return sum
}

private fun diskFragmenter2(input: String): Any {
    val usedSpace = mutableListOf<Pair<Long, LongRange>>()
    val freeSpace = mutableListOf<LongRange>()
    var isFreeSpace = false
    var diskIndex = 0L
    var partIndex = 0L
    input.indices.map { inputIndex ->
        val partSize = input[inputIndex].digitToInt()

        if (!isFreeSpace) {
            usedSpace.add(partIndex to (diskIndex until diskIndex + partSize))
            partIndex++
        } else {
            freeSpace.add(diskIndex until diskIndex + partSize)
        }
        diskIndex += partSize

        isFreeSpace = !isFreeSpace
    }
    usedSpace.reversed().forEachIndexed { index, used ->
        val usedSize = used.second.count()
        val freeSpaceIndex = freeSpace.indexOfFirst { it.count() >= usedSize }
        if (freeSpaceIndex == -1) return@forEachIndexed
        if (freeSpaceIndex >= usedSpace.size - 1 - index) return@forEachIndexed
        val freeSpaceRemoved = freeSpace.removeAt(freeSpaceIndex)
        freeSpace.add(freeSpaceIndex, freeSpaceRemoved.first() + usedSize..freeSpaceRemoved.last)
        usedSpace.removeAt(usedSpace.size - 1 - index)
        usedSpace.add(usedSpace.size - index, used.first to freeSpaceRemoved.first()..<freeSpaceRemoved.first() + usedSize)
    }
    return usedSpace.sumOf { used -> used.second.sumOf { it * used.first } }
}