package gears

import java.io.File
import kotlin.time.measureTimedValue

class PuzzleRunner {
    lateinit var klass: Any
    private val localDir by lazy { File(("src." + klass.javaClass.packageName).replace('.', File.separatorChar)) }
    fun inputLines(fileName: String = "input.txt") = File(localDir, fileName).readLines()
    fun input(fileName: String = "input.txt") = File(localDir, fileName).readText().trim()
}

fun <T> puzzle(title: String = "", code: PuzzleRunner.() -> T): T {
    PuzzleRunner().run {
        this.klass = code
        val paddedTitle = title.padStart(20, ' ') + " "
        val timed = measureTimedValue { code.invoke(this) }
        val paddedRes = if (timed.value !is Unit) timed.value.toString() else ""
        println("$paddedTitle${items.random()} ${paddedRes.padEnd(40)} ⏳ ${timed.duration}")
        return timed.value
    }
}

private val items = listOf(
    "💊", "🎁", "🎉", "🎈", "💣", "⚰️", "💎", "💰", "✈️", "🚀", "🎸", "⚽", "🍺", "🍪",
    "🍕", "🍔", "🍓", "🍉", "🌶", "🌈", "🔥", "🍄", "🌸", "🌻", "🍀", "🦊", "🐱"
)

