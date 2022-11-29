package leetcode

import puzzle

fun main() {
//    runWithTime {
//        ladderLength(
//            "kiss", "tusk", listOf(
//                "miss", "dusk", "kiss", "musk", "tusk", "diss", "disk", "sang", "ties", "muss"
//            )
//        )
//    }
//    runWithTime {
//        ladderLength(
//            "hot", "dog", listOf(
//                "hot", "dog", "hot", "cog", "dog", "tot", "hog", "hop", "pot", "dot"
//            )
//        )
//    }
//    runWithTime {
//        ladderLength("hit", "cog", listOf("hot", "dot", "dog", "lot", "log", "cog"))
//    }
//    runWithTime {
//        ladderLength("hit", "cog", listOf("hot", "dot", "dog", "lot", "log"))
//    }
    puzzle {
        ladderLength(
            "qa",
            "sq",
            listOf(
                "si",
                "go",
                "se",
                "cm",
                "so",
                "ph",
                "mt",
                "db",
                "mb",
                "sb",
                "kr",
                "ln",
                "tm",
                "le",
                "av",
                "sm",
                "ar",
                "ci",
                "ca",
                "br",
                "ti",
                "ba",
                "to",
                "ra",
                "fa",
                "yo",
                "ow",
                "sn",
                "ya",
                "cr",
                "po",
                "fe",
                "ho",
                "ma",
                "re",
                "or",
                "rn",
                "au",
                "ur",
                "rh",
                "sr",
                "tc",
                "lt",
                "lo",
                "as",
                "fr",
                "nb",
                "yb",
                "if",
                "pb",
                "ge",
                "th",
                "pm",
                "rb",
                "sh",
                "co",
                "ga",
                "li",
                "ha",
                "hz",
                "no",
                "bi",
                "di",
                "hi",
                "qa",
                "pi",
                "os",
                "uh",
                "wm",
                "an",
                "me",
                "mo",
                "na",
                "la",
                "st",
                "er",
                "sc",
                "ne",
                "mn",
                "mi",
                "am",
                "ex",
                "pt",
                "io",
                "be",
                "fm",
                "ta",
                "tb",
                "ni",
                "mr",
                "pa",
                "he",
                "lr",
                "sq",
                "ye"
            )
        )
    }
}


fun ladderLength(beginWord: String, endWord: String, wordList: List<String>): Int {
    val set = wordList.toSet()
    if (endWord !in set) return 0

    val queue = ArrayDeque<String>().also { it.add(beginWord) }
    val visited = mutableSetOf<String>()
    var changes = 1

    while (queue.isNotEmpty()) {
        for (i in 0 until queue.size) {
            val word = queue.removeFirst()
            if (word == endWord) return changes

            for (j in word.indices) {
                for (k in 'a'..'z') {
                    val arr = word.toCharArray()
                    arr[j] = k
                    val str = String(arr)
                    if (str in set && str !in visited) {
                        queue.add(str)
                        visited.add(str)
                    }
                }
            }
        }
        changes++
    }
    return 0
}
