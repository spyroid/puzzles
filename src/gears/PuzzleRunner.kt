package gears

import java.io.File
import kotlin.time.measureTimedValue

class PuzzleRunner {
    lateinit var klass: Any
    private val localDir by lazy { File(("src." + klass.javaClass.packageName).replace(".", File.separatorChar.toString())) }
    fun linesFrom(filename: String) = File(localDir, filename).readLines()
    fun allFrom(filename: String) = File(localDir, filename).readText()
}

fun <T> puzzle(title: String = "", code: PuzzleRunner.() -> T): T {
    PuzzleRunner().run {
        this.klass = code
        val paddedTitle = title.padStart(20, ' ') + " "
        val timed = measureTimedValue { code.invoke(this) }
        var paddedRes = ""
        if (timed.value !is Unit) {
            paddedRes = timed.value.toString()
        }
        println("$paddedTitle${items.random()} ${paddedRes.padEnd(30)} ⏳ ${timed.duration}")
        return timed.value
    }
}

private val items = listOf(
    "💊", "🎁", "🎉", "🎈", "💣", "⚰️", "💎", "💰", "✈️", "🚀", "🎸", "⚽", "🍺", "🍪",
    "🍕", "🍔", "🍓", "🍉", "🌶", "🌈", "🔥", "🍄", "🌸", "🌻", "🍀", "🦊", "🐱"
)

infix fun IntRange.isFullyOverlaps(other: IntRange): Boolean = first <= other.first && last >= other.last
infix fun IntRange.isOverlaps(other: IntRange): Boolean = first <= other.last && other.first <= last

// gift from Matsemann
fun <E, F> cartesian(list1: List<E>, list2: List<F>): Sequence<Pair<E, F>> =
    cartesian(listOf(list1, list2)).map { it[0] as E to it[1] as F }

fun <E, F, G> cartesian(list1: List<E>, list2: List<F>, list3: List<G>): Sequence<Triple<E, F, G>> =
    cartesian(listOf(list1, list2, list3)).map { Triple(it[0] as E, it[1] as F, it[2] as G) }

fun <E> cartesian(lists: List<List<E>>): Sequence<List<E>> {
    return sequence {
        val counters = Array(lists.size) { 0 }
        val length = lists.fold(1) { acc, list -> acc * list.size }

        for (i in 0 until length) {
            val result = lists.mapIndexed { index, list ->
                list[counters[index]]
            }
            yield(result)
            for (pointer in lists.size - 1 downTo 0) {
                counters[pointer]++
                if (counters[pointer] == lists[pointer].size) {
                    counters[pointer] = 0
                } else {
                    break
                }
            }
        }
    }
}

fun <T> permutations(list: List<T>): List<List<T>> = when {
    list.size > 10 -> throw Exception("You probably dont have enough memory to keep all those permutations")
    list.size <= 1 -> listOf(list)
    else ->
        permutations(list.drop(1)).map { perm ->
            (list.indices).map { i ->
                perm.subList(0, i) + list.first() + perm.drop(i)
            }
        }.flatten()
}
