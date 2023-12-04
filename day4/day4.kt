import java.io.File

fun pow(n: Int, expo: Int): Int {
    var res = 1
    for (i in 0 ..< expo) {
        res *= n
    }
    return res
}

fun two_pow(n: Int): Int {
    var res = 1
    for (i in 0..n) {
        res *= 2
    }
    return res
}

fun main1() {
    var lines = File("input.txt").readLines()
    var count = 0
    for (line in lines) {
        var hand_win_count = 0
        var num_split = line.replace("  ", " ").split(":")[1]
        var nums = num_split.split(" | ")
        var winning = nums[0].trim().split(" ").map { a: String -> Integer.parseInt(a) }
        var hand = nums[1].trim().split(" ").map { a: String -> Integer.parseInt(a) }
        for (n in hand) {
            if (n in winning) {
                hand_win_count++
            }
        }
        if (hand_win_count > 0) {
            count += pow(2, hand_win_count - 1)
        }
    }
    println(count)
}

fun main() {
    var lines = File("input.txt").readLines()
    var copies = HashMap<Int, Int>()

    for (line in lines) {
        var hand_win_count = 0
        var card = Integer.parseInt(line.split(":")[0].removePrefix("Card ").trim())
        var num_split = line.replace("  ", " ").split(":")[1]
        var nums = num_split.split(" | ")
        var winning = nums[1].trim().split(" ").map { a: String -> Integer.parseInt(a) }
        var hand = nums[0].trim().split(" ").map { a: String -> Integer.parseInt(a) }
        for (n in hand) {
            if (n in winning) {
                hand_win_count++
            }
        }
        for (i in (card + 1)..(card + hand_win_count)) {
            copies.set(i, copies.getOrDefault(i, 0) + copies.getOrDefault(card, 0) + 1)
        }
    }
    println(copies.values.sum() + lines.size)
}
