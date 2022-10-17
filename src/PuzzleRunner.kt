import java.io.File

class PuzzleRunner {
    lateinit var local: Any
    private val baseDir = "src."
    private fun localDir(klass: Any) = File((baseDir + klass.javaClass.packageName).replace(".", File.separatorChar.toString()))
    private fun readLocal(klass: Any, fileName: String) = File(localDir(klass), fileName).readLines()
    fun dataFrom(filename: String) = readLocal(local, filename)
}

fun <T> puzzle(code: PuzzleRunner.() -> T): PuzzleRunner {
    return PuzzleRunner().apply {
        this.local = code
        val startTime = System.nanoTime()
        val result: T = code.invoke(this)
        val endTime = System.nanoTime()
        println("💥 Result: $result Time: ${(endTime - startTime) / 1_000_000}")
    }
}
