package aoc.y2015.day4

import gears.puzzle
import java.security.MessageDigest
import kotlin.text.Charsets.UTF_8

private fun main() {
    puzzle { part("abcdef", 5) }
    puzzle { part("ckczppom", 5) }
    puzzle { part("abcdef", 6) }
    puzzle { part("ckczppom", 6) }
}

private fun part(s: String, prefixLength: Int): Int {
    for (i in 1..Int.MAX_VALUE) {
        if (md5(s + i).isZeros(prefixLength)) {
//            println(md5(s + i).toHex())
            return i
        }
    }
    return 0
}

private val dig = MessageDigest.getInstance("MD5")
private fun md5(str: String): ByteArray = dig.digest(str.toByteArray(UTF_8))
private fun ByteArray.isZeros(n: Int): Boolean {
    repeat(n / 2) {
        if (this[it] != 0.toByte()) return false
    }
    if (n % 2 != 0) {
        if (this[n / 2].toUByte() > 15.toUByte()) return false
    }
    return true
}

