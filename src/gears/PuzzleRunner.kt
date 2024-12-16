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
    return PuzzleRunner().run {
        this.klass = code
        val paddedTitle = title.padStart(20, ' ') + " "
        val (value, timeTaken) = measureTimedValue { code.invoke(this) }
        val paddedRes = if (value !is Unit) value.toString() else ""
        println("$paddedTitle${items.random()} ${paddedRes.padEnd(40)}${timeTaken}")
        return value
    }
}

private val items = listOf(
    "💊", "🎁", "🎉", "🎈", "💣", "⚰️", "💎", "💰", "✈️", "🚀", "🎸", "⚽", "🍺", "🍪",
    "🍕", "🍔", "🍓", "🍉", "🌶", "🌈", "🔥", "🍄", "🌸", "🌻", "🍀", "🦊", "🐱"
)
