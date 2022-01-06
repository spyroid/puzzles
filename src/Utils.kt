import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readRawInput(name: String) = File("src", "$name.txt").readText()
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun List<String>.toIntSeq(): Sequence<Int> {
    return this.asSequence().map { it.toInt() }
}

fun readIntsAsSeq(name: String): Sequence<Int> = readInput(name).joinToString().split(",").map { it.toInt() }.asSequence()

fun readToIntSeq(name: String) = readInput(name).toIntSeq()

fun readInt(name: String) = readInput(name).map { it.toInt() }

infix fun Int.toward(to: Int): IntProgression {
    val step = if (this > to) -1 else 1
    return IntProgression.fromClosedRange(this, to, step)
}
