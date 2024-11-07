package other

import gears.puzzle

fun main() {
    puzzle {
        solve(0, listOf(2, 3, 2, 2).toIntArray(), false)
    }
}

private fun solve(clawPos: Int, boxes: IntArray, boxInClaw: Boolean): Any {
    var layout = (boxes.sum() + boxInClaw.compareTo(false)).let { all ->
        boxes.mapIndexed { i, _ -> all / boxes.size + ((all % boxes.size) > i).compareTo(false) }
    }

    println("current: ${boxes.toList()} pos: $clawPos boxInClaw: $boxInClaw")
    println("planned:  $layout")

    if (boxInClaw) {
        val target = layout.withIndex().find { (i, v) -> v > boxes[i] }?.index ?: return "ERR"
//        println("target index: $target")
        when (target.compareTo(clawPos)) {
            -1 -> return "LEFT"
            0 -> return "PLACE"
            1 -> return "RIGHT"
        }
    } else {
        val target = layout.withIndex().find { (i, v) -> v < boxes[i] }?.index ?: return "ERR"
//        println("target index: $target")
        when (target.compareTo(clawPos)) {
            -1 -> return "LEFT"
            0 -> return "PICK"
            1 -> return "RIGHT"
        }
    }

    return "NOTHING"
}
