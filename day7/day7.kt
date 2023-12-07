import java.io.File

fun hand_rank(hand: String): Int {
    var set = hand.toSet()
    var counts = ArrayList<Int>()
    for (c in set) {
        counts.add(hand.filter { it == c }.length)
    }
    counts.sort()

    if (set.size == 1) { // Five of a kind
        return 6
    }
    if (set.size == 2 && counts[1] == 4) { // Four of a kind
        return 5
    }
    if (set.size == 2) { // Full house
        return 4
    }
    if (set.size == 3 && counts[2] == 3) { // Three of a kind
        return 3
    }
    if (set.size == 3) { // Two pair
        return 2
    }
    if (set.size == 4) { // Pair
        return 1
    }
    return 0
}

fun hand_rank_j(hand: String): Int {
    var j_count = hand.filter { it == 'J' }.length
    var h = hand.filter { it != 'J' }
    var set = h.toSet()
    var counts = ArrayList<Int>()

    for (c in set) {
        counts.add(h.filter { it == c }.length)
    }
    counts.sort()

    if (set.size == 1 || set.size == 0) { // Five of a kind
        return 6
    }
    if (set.size == 2 && counts[1] + j_count == 4) { // Four of a kind
        return 5
    }
    if (set.size == 2) { // Full house
        return 4
    }
    if (set.size == 3 && counts[2] + j_count == 3) { // Three of a kind
        return 3
    }
    if (set.size == 3) { // Two pair
        return 2
    }
    if (set.size == 4) { // Pair
        return 1
    }
    return 0
}

fun compare_hands(hand1: String, hand2: String): Int {
    var values = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
    var diff = hand_rank(hand1) - hand_rank(hand2)
    if (diff != 0) {
        return diff
    }

    for (i in 0..4) {
        diff = values.indexOf(hand2[i]) - values.indexOf(hand1[i])
        if (diff != 0) {
            return diff
        }
    }
    return 0
}

fun compare_hands_j(hand1: String, hand2: String): Int {
    var values = listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')
    var diff = hand_rank_j(hand1) - hand_rank_j(hand2)
    if (diff != 0) {
        return diff
    }

    for (i in 0..4) {
        diff = values.indexOf(hand2[i]) - values.indexOf(hand1[i])
        if (diff != 0) {
            return diff
        }
    }
    return 0
}

fun main() {
    var lines = File("input.txt").readLines().map { it.split(" ") }
    var cards = lines.map { Pair(it[0], Integer.parseInt(it[1])) }
    cards = cards.sortedWith { h1, h2 -> compare_hands_j(h1.first, h2.first) }

    var sum = 0
    for (i in 0 ..< cards.size) {
        sum += cards[i].second * (i + 1)
    }
    println(sum)
}
