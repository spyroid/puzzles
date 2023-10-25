package gears

enum class Direction(var x: Int, var y: Int) {
    RIGHT(1, 0),
    DOWN(0, -1),
    LEFT(-1, 0),
    UP(0, 1),
    DOWN_RIGHT(1, -1),
    DOWN_LEFT(-1, -1),
    UP_RIGHT(1, 1),
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
        fun of(s: String) = of(s.getOrElse(0) { '!' })
        fun of(ch: Char) =
            when (ch) {
                'N', 'U', '^' -> UP
                'S', 'D', 'v', 'V' -> DOWN
                'E', 'R', 'F', '>' -> RIGHT
                'W', 'L', '<' -> LEFT
                else -> NOTHING
            }

        fun setYDown() {
            DOWN.y = 1
            UP.y = -1
        }
    }
}
