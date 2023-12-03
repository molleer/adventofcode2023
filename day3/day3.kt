import java.io.File
import kotlin.math.max
import kotlin.math.min

fun is_symbol(char: Char): Boolean {
    return !char.isDigit() && char != '.'
}

fun has_adjacent_symbol(x: Int, y: Int, len: Int, lines: List<String>): Boolean {
    for (y_offset in -1..1) {
        var y_pos = max(0, min(lines[y].length - 1, y + y_offset))
        var line = lines[y_pos]
        for (x_offset in -1..(len)) {
            var x_pos = max(0, min(line.length - 1, x + x_offset))
            if (is_symbol(line.get(x_pos))) {
                return true
            }
        }
    }
    return false
}

fun main1() {
    var lines = File("input.txt").readLines()
    var re = Regex("[0-9]+")
    var sum = 0
    var count = 0
    for (y in 0 ..< lines.size) {
        for (res in re.findAll(lines[y])) {
            count++
            var v = res.value
            if (has_adjacent_symbol(res.range.first(), y, res.value.length, lines)) {
                println("$v has symbol")
                sum += Integer.parseInt(res.value)
            } else {
                println("$v has no symbol")
            }
        }
    }
    println(count)
    println(sum)
}

/** Returns <val, x_start_pos> */
fun get_adjacent_number(x: Int, y: Int, lines: List<String>): Pair<Int, Int> {
    var re = Regex("[0-9]+")
    for (match in re.findAll(lines[y])) {
        if (x in match.range) {
            return Pair(Integer.parseInt(match.value), match.range.first())
        }
    }
    return Pair(-1, -1)
}

fun get_adjacent_numbers(x: Int, y: Int, lines: List<String>): List<Int> {
    var numbers = ArrayList<Int>()
    for (y_offset in -1..1) {
        var y_pos = max(0, min(lines[y].length - 1, y + y_offset))
        var line = lines[y_pos]
        var new_numbers = HashSet<Pair<Int, Int>>()
        for (x_offset in -1..1) {
            var x_pos = max(0, min(line.length - 1, x + x_offset))
            if (line.get(x_pos).isDigit()) {
                new_numbers.add(get_adjacent_number(x_pos, y_pos, lines))
            }
        }
        numbers.addAll(new_numbers.map { it.component1() })
    }
    return numbers
}

fun main() {
    var lines = File("input.txt").readLines()
    var re = Regex("\\*")
    var sum = 0
    for (y in 0 ..< lines.size) {
        for (res in re.findAll(lines[y])) {
            var nums = get_adjacent_numbers(res.range.first(), y, lines)
            if (nums.size == 2) {
                sum += nums[0] * nums[1]
            }
        }
    }
    println(sum)
}
