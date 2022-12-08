import java.io.File
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

class PuzzleRunner {
    lateinit var local: Any
    private val baseDir = "src."
    private fun localDir(klass: Any) = File((baseDir + klass.javaClass.packageName).replace(".", File.separatorChar.toString()))
    private fun readLocal(klass: Any, fileName: String) = File(localDir(klass), fileName)
    fun linesFrom(filename: String) = readLocal(local, filename).readLines()
}

@OptIn(ExperimentalTime::class)
fun <T> puzzle(title: String = "", code: PuzzleRunner.() -> T): PuzzleRunner {
    return PuzzleRunner().apply {
        this.local = code
        val paddedTitle = title.padStart(15, ' ') + " "
        val timed = measureTimedValue { code.invoke(this) }
        if (timed.value is Unit) {
            println("$paddedTitle⌛️ ${timed.duration}")
        } else {
            val paddedRes = timed.value.toString().padEnd(20)
            println("$paddedTitle${items.random()} $paddedRes ⏳ ${timed.duration}")
        }
    }
}

private val items = listOf(
    "💊", "🎁", "🎉", "🎈", "💣", "⚰️", "💎", "💰", "✈️", "🚀", "🎸", "⚽", "️🍺", "🍪",
    "🍕", "🍔", "🍓", "🍉", "🌶", "🌈", "🔥", "🍄", "🌸", "🌻", "🍀", "🦊", "🐱"
)

infix fun IntRange.isFullyOverlaps(other: IntRange): Boolean = first <= other.first && last >= other.last
infix fun IntRange.isOverlaps(other: IntRange): Boolean = first <= other.last && other.first <= last

typealias Array2d<T> = List<List<T>>

fun <T> Array2d<T>.rotate2D(): Array2d<T> = List(this[0].size) { i -> List(this.size) { j -> this[j][i] } }
fun <T> Array2d<T>.at(x: Int, y: Int): T? = if (y in indices && x in first().indices) this[y][x] else null

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
