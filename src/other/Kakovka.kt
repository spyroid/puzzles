package other

fun main() {
    calculateLeaderboardPlaces(listOf(User("id1", 3), User("id2", 2), User("id3", 1)), listOf(100, 50, 10))
    calculateLeaderboardPlaces(listOf(User("id1", 100), User("id2", 3), User("id3", 2), User("id4", 1)), listOf(100, 50, 10))
    calculateLeaderboardPlaces(listOf(User("id1", 55)), listOf(100, 50, 10))
}

private data class User(val userId: String, val score: Int)
private fun calculateLeaderboardPlaces(users: List<User>, scores: List<Int>): List<Pair<String, Int>> {
    var i = 0
    return users.sortedByDescending { it.score }.map { user ->
        scores.sortedByDescending { it }.count { it <= user.score }.let { user.userId to if (it == 0) 4 + i++ else 4 - it }
    }.onEach { println(it) }.also { println() }
}