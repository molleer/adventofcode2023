import java.io.File

fun first_digit(line: String): Int {
    var digits =
            mapOf(
                    "one" to 1,
                    "two" to 2,
                    "three" to 3,
                    "four" to 4,
                    "five" to 5,
                    "six" to 6,
                    "seven" to 7,
                    "eight" to 8,
                    "nine" to 9
            )
    for (k in 0..line.length) {
        if (k < line.length && line.get(k).isDigit()) {
            return line.get(k).code - 48
        }
        for (key in digits.keys) {
            if (key in line.substring(0, k)) {
                return digits.getOrDefault(key, 0)
            }
        }
    }
    return 0
}

fun last_digit(line: String): Int {
    var digits =
            mapOf(
                    "one" to 1,
                    "two" to 2,
                    "three" to 3,
                    "four" to 4,
                    "five" to 5,
                    "six" to 6,
                    "seven" to 7,
                    "eight" to 8,
                    "nine" to 9
            )
    for (k in line.length downTo 0) {
        if (k < line.length && line.get(k).isDigit()) {
            return line.get(k).code - 48
        }
        for (key in digits.keys) {
            if (key in line.substring(k, line.length)) {
                return digits.getOrDefault(key, 0)
            }
        }
    }
    return 0
}

fun main() {
    var lines = File("input.txt").readLines()
    var sum = 0
    for (line in lines) {
        sum += (first_digit(line) * 10 + last_digit(line))
    }

    println(sum)
}
