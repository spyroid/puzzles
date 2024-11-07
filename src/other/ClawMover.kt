package other

import gears.puzzle

fun main() {
    puzzle {
        solve(0, listOf(2, 3, 2, 2).toIntArray(), true)
    }
}

private fun solve(clawPos: Int, boxes: IntArray, boxInClaw: Boolean): Any {
    var layout = (boxes.sum() + boxInClaw.compareTo(false)).let { all ->
        boxes.mapIndexed { i, _ -> all / boxes.size + ((all % boxes.size) > i).compareTo(false) }
    }
    return layout.withIndex().find { (i, v) -> (v < boxes[i]) xor boxInClaw }?.index.let { target ->
        when (target?.compareTo(clawPos)) {
            -1 -> "LEFT"
            0 -> if (boxInClaw) "PLACE" else "PICK"
            1 -> "RIGHT"
            else -> "NOTHING"
        }
    }
}