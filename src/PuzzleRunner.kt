import java.io.File
import java.net.URL
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

class PuzzleRunner {
    lateinit var local: Any
    private val baseDir = "src."
    private fun localDir(klass: Any) = File((baseDir + klass.javaClass.packageName).replace(".", File.separatorChar.toString()))
    private fun readLocal(klass: Any, fileName: String) = File(localDir(klass), fileName)
    fun readLinesFrom(filename: String) = readLocal(local, filename).readLines()
    fun readLinesFromUrl(url: String): List<String> {
        URL(url).openStream().use {
            return it.bufferedReader().lines().toList()
        }
    }
}

@OptIn(ExperimentalTime::class)
fun <T> puzzle(code: PuzzleRunner.() -> T): PuzzleRunner {
    return PuzzleRunner().apply {
        this.local = code
        val timed = measureTimedValue { code.invoke(this) }
        if (timed.value is Unit) {
            println("⌛️ ${timed.duration}")
        } else {
            val paddedRes = timed.value.toString().padEnd(20)
            println("${items.random()} $paddedRes ⏳ ${timed.duration}")
        }
    }
}

private val items = listOf(
    "💊", "🎁", "🎉", "🎈", "💣", "⚰️", "💎", "💰", "✈️", "🚀", "🎸", "⚽", "️🍺", "🍪",
    "🍕", "🍔", "🍓", "🍉", "🌶", "🌈", "🔥", "🍄", "🌸", "🌻", "🍀", "🦊", "🐱"
)

infix fun IntRange.isFullyOverlaps(other: IntRange): Boolean = first <= other.first && last >= other.last
infix fun IntRange.isOverlaps(other: IntRange): Boolean = first <= other.last && other.first <= last

