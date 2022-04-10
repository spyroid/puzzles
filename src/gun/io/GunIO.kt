package gun.io

fun main() {
    println("1: ${epigram("Dealing with failure is easy: Work hard to improve. Success is also easy to handle: You've solved the wrong problem. Work hard to improve.")}")
    println("2: ${fibo(10_000)}")
    println("3: ${legion(2660)}")
}

fun legion(i: Int): Int {
    fun intToRoman(num: Int): String {
        val M = arrayOf("", "M", "MM", "MMM")
        val C = arrayOf("", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM")
        val X = arrayOf("", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC")
        val I = arrayOf("", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX")
        return M[num / 1000] + C[num % 1000 / 100] + X[num % 100 / 10] + I[num % 10]
    }
    return (1..i).map { intToRoman(it) }.sumOf { it.count { c -> c == 'X' } }
}

fun epigram(s: String): Int {
    val vowels = setOf('a', 'e', 'i', 'u', 'o')
    return s.asSequence()
        .filter { it.isLetter() }
        .map { if (vowels.contains(it)) -it.code else it.code }
        .sum()
}

fun fibo(limit: Int): Int {
    var prev = 0
    return generateSequence(1) { v -> (prev + v).also { prev = v } }
        .filter { it % 2 != 0 }
        .takeWhile { it < limit }.sum()
}

