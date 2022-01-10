package codingame.offset_arrays

import readLocal
import kotlin.system.measureTimeMillis

class OffsetArrays(private val input: List<String>) {
    fun solve(): String {
        val map = mutableMapOf<String, List<Int>>()
        (1 until input.lastIndex).forEach { i ->
            val parts = input[i].split("[", "..", " = ")
            val list = parts.last().split(" ").map { it.toInt() }.toMutableList()
            list.add(0, parts[1].toInt())
            map[parts.first()] = list
        }

        return generateSequence(ArrayDeque(input.last().replace("]", "").split("["))) {
            val idx = it.removeLast().toInt()
            val obj = it.removeLast()
            val arr = map[obj]!!
            it.addLast(arr[idx - arr[0] + 1].toString())
            it
        }.first { it.size == 1 }.first()
    }
}

fun main() {
    measureTimeMillis { print("⭐️ Result: ${OffsetArrays(readLocal(Main(), "input.txt")).solve()}") }
        .also { time -> println(" in $time ms") }
}

class Main {}
