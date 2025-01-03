package aoc.y2016.day5

import gears.puzzle
import gears.toHex
import java.security.MessageDigest

fun main() {
    puzzle { chess("ugkcyxxp") }
    puzzle { chess2("ugkcyxxp") }
}

private fun chess2(id: String): String {
    val password = Array(8) { '.' }.toMutableList()
    for (i in 1..Int.MAX_VALUE) {
        if (md5("$id$i").hasLeadingZeros(5)) {
            val md5 = md5("$id$i").toHex()
            val i = md5[5].digitToIntOrNull() ?: continue
            if (i > 7 || password[i] != '.') continue
            password[i] = md5[6]
            if (password.count { it == '.' } == 0) break
        }
    }
    return password.joinToString("")
}

private fun chess(id: String): String {
    return buildString {
        for (i in 1..Int.MAX_VALUE) {
            if (md5("$id$i").hasLeadingZeros(5)) {
                append(md5("$id$i").toHex()[5])
                if (length == 8) break
            }
        }
    }
}
private fun ByteArray.hasLeadingZeros(n: Int): Boolean {
    repeat(n / 2) { if (this[it] != 0.toByte()) return false }
    return !(n % 2 != 0 && this[n / 2].toUByte() > 15.toUByte())
}
private fun md5(s: String) = MessageDigest.getInstance("MD5").digest(s.toByteArray())
