import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

const val baseDir = "src."

private fun localDir(klass: Any) = File((baseDir + klass.javaClass.packageName).replace(".", File.separatorChar.toString()))

fun readLocal(klass: Any, fileName: String) = File(localDir(klass), fileName).readLines()

fun readRawInput(name: String) = File("src", "$name.txt").readText()

fun readInput(name: String) = File("src", "$name.txt").readLines()

fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun List<String>.toIntSeq() = this.asSequence().map { it.toInt() }

fun readIntsAsSeq(name: String): Sequence<Int> = readInput(name).joinToString().split(",").map { it.toInt() }.asSequence()

fun readToIntSeq(name: String) = readInput(name).toIntSeq()

infix fun Int.toward(to: Int) = IntProgression.fromClosedRange(this, to, if (this > to) -1 else 1)

