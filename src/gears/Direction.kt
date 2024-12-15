package gears

enum class Direction(var x: Int, var y: Int) {
    UP(0, 1),
    UP_RIGHT(1, 1),
    RIGHT(1, 0),
    DOWN_RIGHT(1, -1),
    DOWN(0, -1),
    DOWN_LEFT(-1, -1),
    LEFT(-1, 0),
    UP_LEFT(-1, 1),
    NOTHING(0, 0);

    fun turnCw() =
        when (this) {
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
            UP -> RIGHT
            else -> NOTHING
        }

    fun turnCcw() =
        when (this) {
            RIGHT -> UP
            DOWN -> RIGHT
            LEFT -> DOWN
            UP -> LEFT
            else -> NOTHING
        }

    fun flip() =
        when (this) {
            RIGHT -> LEFT
            LEFT -> RIGHT
            UP -> DOWN
            DOWN -> UP
            else -> NOTHING
        }

    fun asPoint() = Point(x, y)

    companion object {
        fun of(s: String, yFlip: Boolean = false) = of(s.getOrElse(0) { '!' }, yFlip)
        fun of(ch: Char, yFlip: Boolean = false) =
            when (ch) {
                'N', 'U', '^' -> if (yFlip) DOWN else UP
                'S', 'D', 'v', 'V' -> if (yFlip) UP else DOWN
                'E', 'R', 'F', '>' -> RIGHT
                'W', 'L', '<' -> LEFT
                else -> NOTHING
            }

        fun setYDown() {
            DOWN.y = 1
            UP.y = -1
        }

        fun all4() = listOf(UP, RIGHT, DOWN, LEFT)
        fun all8() = listOf(UP, UP_RIGHT, RIGHT, DOWN_RIGHT, DOWN, DOWN_LEFT, LEFT, UP_LEFT)
    }
}
