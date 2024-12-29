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
        val (value, timeTaken) = measureTimedValue { code.invoke(this) }
        val res = if (value !is Unit) value.toString() else ""
        println("${items.random()} ${res.padEnd(maxOf(40, res.length))}  ${timeTaken}")
        return value
    }
}

private val items = listOf(
    "💊", "🎁", "🎉", "🎈", "💣", "⚰️", "💎", "💰", "✈️", "🚀", "🎸", "⚽", "🍺", "🍪",
    "🍕", "🍔", "🍓", "🍉", "🌶", "🌈", "🔥", "🍄", "🌸", "🌻", "🍀", "🦊", "🐱"
)
