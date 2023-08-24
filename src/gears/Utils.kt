package gears

import java.io.File
import java.security.MessageDigest

const val baseDir = "src."

private fun localDir(klass: Any) =
    File((baseDir + klass.javaClass.packageName).replace(".", File.separatorChar.toString()))

fun readLocal(klass: Any, fileName: String) = File(localDir(klass), fileName).readLines()

fun readRawInput(name: String) = File("src", "$name.txt").readText()

fun readInput(name: String) = File("src", "$name.txt").readLines()

fun List<String>.toIntSeq() = this.asSequence().map { it.toInt() }
fun List<String>.toLongs() = this.map { it.toLong() }
fun List<String>.toInts() = this.map { it.toInt() }

fun readIntsAsSeq(name: String): Sequence<Int> =
    readInput(name).joinToString().split(",").map { it.toInt() }.asSequence()

fun readToIntSeq(name: String) = readInput(name).toIntSeq()

private val dig = MessageDigest.getInstance("MD5")
fun md5(str: String): ByteArray = dig.digest(str.toByteArray(Charsets.UTF_8))
fun ByteArray.hasLeadingZeros(n: Int): Boolean {
    repeat(n / 2) { if (this[it] != 0.toByte()) return false }
    return !(n % 2 != 0 && this[n / 2].toUByte() > 15.toUByte())
}

fun ByteArray.toHex(): String = java.util.HexFormat.of().formatHex(this)

