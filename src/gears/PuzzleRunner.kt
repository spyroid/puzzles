package gears

import java.io.File
import kotlin.time.measureTimedValue

class PuzzleRunner {
    lateinit var klass: Any
    private val localDir by lazy { File(("src." + klass.javaClass.packageName).replace(".", File.separatorChar.toString())) }
    fun linesFrom(filename: String) = File(localDir, filename).readLines()
    fun allFrom(filename: String) = File(localDir, filename).readText()
    fun input() = allFrom("input.txt")
    fun inputLines() = linesFrom("input.txt")
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
        println("$paddedTitle${items.random()} ${paddedRes.padEnd(40)} ⏳ ${timed.duration}")
        return timed.value
    }
}

private val items = listOf(
    "💊", "🎁", "🎉", "🎈", "💣", "⚰️", "💎", "💰", "✈️", "🚀", "🎸", "⚽", "🍺", "🍪",
    "🍕", "🍔", "🍓", "🍉", "🌶", "🌈", "🔥", "🍄", "🌸", "🌻", "🍀", "🦊", "🐱"
)

