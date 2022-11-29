import java.io.File
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

class PuzzleRunner {
    lateinit var local: Any
    private val baseDir = "src."
    private fun localDir(klass: Any) = File((baseDir + klass.javaClass.packageName).replace(".", File.separatorChar.toString()))
    private fun readLocal(klass: Any, fileName: String) = File(localDir(klass), fileName).readLines()
    fun dataFrom(filename: String) = readLocal(local, filename)
}

@OptIn(ExperimentalTime::class)
fun <T> puzzle(code: PuzzleRunner.() -> T): PuzzleRunner {
    return PuzzleRunner().apply {
        this.local = code
        val timed  = measureTimedValue { code.invoke(this) }
        if (timed.value is Unit) {
            println("🕘 ${timed.duration}")
        } else {
            println("💥 ${timed.value} 🕑 ${timed.duration}")
        }
    }
}
