import java.io.File

const val baseDir = "src."

private fun localDir(klass: Any) = File((baseDir + klass.javaClass.packageName).replace(".", File.separatorChar.toString()))

fun readLocal(klass: Any, fileName: String) = File(localDir(klass), fileName).readLines()

fun readRawInput(name: String) = File("src", "$name.txt").readText()

fun readInput(name: String) = File("src", "$name.txt").readLines()

fun List<String>.toIntSeq() = this.asSequence().map { it.toInt() }

fun readIntsAsSeq(name: String): Sequence<Int> = readInput(name).joinToString().split(",").map { it.toInt() }.asSequence()

fun readToIntSeq(name: String) = readInput(name).toIntSeq()
