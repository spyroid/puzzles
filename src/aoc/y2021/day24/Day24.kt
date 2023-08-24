package aoc.y2021.day24

// Some thoughts about that
// https://docs.google.com/spreadsheets/d/1eSizAHZFgb7XiQsDK7oG2U-f8c0M01F-c8K9nf68akg/edit?usp=sharing

fun calc(ww: Int, zz: Int = 0, p1: Int = 0, p2: Int = 0, p3: Int = 0): Int {
//    var z = zz
    //    var y = 0
//    var x = 0 // mul x 0
//    var x = zz
//    var x = (zz % 26) + p2 // param
    var z = zz / p1 // param
//    x += 10 // param
//    x = if (x == ww) 1 else 0
//    x = if (x == 0) 1 else 0
    val x = if ((zz % 26) + p2 == ww) 0 else 1
//    var y = 0
    val y = 26 + x
//    y += x
//    y += 1
    z = z * y
//    y = (ww + p3) * x
//    y += w
//    y += 2 // param
//    y *= x
    z = z + ((ww + p3) * x)
//    println("w=$ww x=$x y=$y z=$z")
    return z
}

fun calc1(ww: Int, zz: Int = 0, p1: Int = 0, p2: Int = 0, p3: Int = 0) {
    var z = 0 // param
    val x = 1//if ((zz % 26) + 10 == ww) 0 else 1
//    val y = 26 + 1
//    z = z * y
    z = ww + 2
}

fun calc5(ww: Int, zz: Int = 0, p1: Int = 0, p2: Int = 0, p3: Int = 0) {
    var z = zz / 26 // param
    val x = if ((zz % 26) - 8 == ww) 0 else 1
    val y = 26 + x
    z *= y
    z += ((ww + 1) * x)
}

val p1 = intArrayOf( 1,  1,  1,  1, 26,  1, 26, 26, 1, 26, 1, 26, 26, 26)
val p2 = intArrayOf(10, 15, 14, 15, -8, 10, -16, -4, 11, -3, 12, -7, -15, -7)
val p3 = intArrayOf( 2, 16,  9,  0,  1, 12, 6, 6, 3, 5, 9, 3, 2, 3)

fun backwards(i: Int, zz: Int, skip: Set<Int>): Pair<Int, Int> {
    for (w in 9 downTo 1) for (z in 26 downTo 1) {
        var rz = calc(ww = w, zz = z, p1 = p1[i], p2 = p2[i], p3 = p3[i])
//        println(rz)
        if (rz == zz && z !in skip) {
            return Pair(w, z)
//            println("$w $z")
        }
    }
    return Pair(0, 0)
}

fun backwardsAll(i: Int, zz: Int): List<Pair<Int, Int>> {
    val all = mutableListOf<Pair<Int, Int>>()
    for (w in 9 downTo 1) for (z in 26 downTo 1) {
        val rz = calc(ww = w, zz = z, p1 = p1[i], p2 = p2[i], p3 = p3[i])
        if (rz == zz) all.add(Pair(w, z))
    }
    return all
}

fun main() {
    fun part1(input: List<String>): Int {

        var zz = 0
        var i = 13
        val nums = ArrayDeque<Int>()

        fun deeper(i: Int, zz: Int): Boolean {
            if (i == 0) return true
            val all1 = backwardsAll(i, zz)
            for (a in all1) {
                println("Trying $i $a ")
                val all2 = backwardsAll(i - 1, a.second)
                if (all2.isNotEmpty()) {
                    nums.addFirst(a.first)
                    val res = deeper(i - 1, a.second)
                    if (res) break
                }
            }
            nums.removeFirst()
            return false
        }

        deeper(13, 0)
        return 0
    }

    fun part2(input: List<String>) = 0

//    check(part1(gears.readInput("day24/input")) == 1)
//    measureTimeMillis { print("⭐️ Part1: ${part1(readInput("day24/test"))}") }.also { time -> println(" in $time ms") }

//    check(part2(4, 8) == 1)
//    measureTimeMillis { print("⭐️ Part2: ${part2(7, 1)}") }.also { time -> println(" in $time ms") }
}

