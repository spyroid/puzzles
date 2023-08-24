package gears

import java.security.MessageDigest

fun List<String>.toInts() = this.map { it.toInt() }

private val dig = MessageDigest.getInstance("MD5")

fun md5(str: String): ByteArray = dig.digest(str.toByteArray(Charsets.UTF_8))

fun ByteArray.hasLeadingZeros(n: Int): Boolean {
    repeat(n / 2) { if (this[it] != 0.toByte()) return false }
    return !(n % 2 != 0 && this[n / 2].toUByte() > 15.toUByte())
}

fun ByteArray.toHex(): String = java.util.HexFormat.of().formatHex(this)

fun Int.bitCount1(): Int {
    var vv = this
    var count = 0
    while (vv != 0) count += (vv and 1).also { vv = vv shr 1 }
    return count
}
