package aoc.y2021.day16


class Decoder(input: String) {

    data class Packet(val version: Int, val type: Int) {
        val packets = mutableListOf<Packet>()
        var value = 0L

        fun getAllVersions(): List<Int> = buildList {
            add(version)
            packets.forEach { addAll(it.getAllVersions()) }
        }

        fun value(): Long {
            return when (type) {
                0 -> packets.sumOf { it.value() }
                1 -> packets.map { it.value() }.reduce { a, b -> a * b }
                2 -> packets.minOf { it.value() }
                3 -> packets.maxOf { it.value() }
                4 -> value
                5 -> if (packets.first().value() > packets.last().value()) 1 else 0
                6 -> if (packets.first().value() < packets.last().value()) 1 else 0
                7 -> if (packets.first().value() == packets.last().value()) 1 else 0
                else -> -1
            }
        }
    }

    private val binData = buildString {
        input.asSequence()
            .map { it.digitToInt(16).toString(2) }
            .forEach { append(it.padStart(4, '0')) }
    }

    val root = decodePacket(binData).first

    private fun decodePacket(data: String): Pair<Packet, String> {
        var local = data
        val version = local.take(3).toInt(2).also { local = local.drop(3) }
        val type = local.take(3).toInt(2).also { local = local.drop(3) }
        val packet = Packet(version, type)

        if (type == 4) {
            packet.value = buildString {
                while (local.first() == '1') {
                    append(local.drop(1).take(4))
                    local = local.drop(5)
                }
                append(local.drop(1).take(4))
                local = local.drop(5)
            }.toLong(2)
        } else {
            val lenType = local.take(1).toInt(2).also { local = local.drop(1) }
            if (lenType == 0) {
                val totalLength = local.take(15).toInt(2).also { local = local.drop(15) }
                var subData = local.take(totalLength).also { local = local.drop(totalLength) }
                while (subData.isNotEmpty()) {
                    val (pkt, remaining) = decodePacket(subData)
                    packet.packets.add(pkt)
                    subData = remaining
                }
            } else {
                val subPacketCount = local.take(11).toInt(2).also { local = local.drop(11) }
                while (packet.packets.size < subPacketCount) {
                    val (pkt, remaining) = decodePacket(local)
                    local = remaining
                    packet.packets.add(pkt)
                }
            }
        }
        return Pair(packet, local)
    }
}

fun main() {

    fun part1(input: List<String>): Int {
        return Decoder(input.first()).root.getAllVersions().sum()
    }

    fun part2(input: List<String>): Long {
        return Decoder(input.first()).root.value()
    }


//    val testData = readInput("day16/test")
//    val inputData = readInput("day16/input")
//
//    var res1 = part1(testData)
//    check(res1 == 31) { "Expected 31 but got $res1" }
//
//    var time = measureTimeMillis { res1 = part1(inputData) }
//    println("⭐️ Part1: $res1 in $time ms")
//
//    var res2: Long
//    time = measureTimeMillis { res2 = part2(inputData) }
//    println("⭐️ Part2: $res2 in $time ms")
}

