import java.io.File
import kotlin.math.min
import kotlin.sequences.sequence

fun may_fold_h(line: String, x: Int): Boolean {
    for (i in 0..min(x, line.length - x - 2)) {
        if (line[x - i] != line[x + i + 1]) {
            return false
        }
    }
    return true
}

fun may_fold_v(lines: List<String>, y_fold: Int, x: Int): Boolean {
    for (i in 0..min(y_fold, lines.size - y_fold - 2)) {
        if (lines[y_fold - i][x] != lines[y_fold + i + 1][x]) {
            return false
        }
    }
    return true
}

fun reflection_value(lines: List<String>, ignore: Int = -1): Int {
    var h_foldable = List(lines[0].length - 1) { it }.filter { it != ignore - 1 }
    for (line in lines) {
        h_foldable = h_foldable.filter { may_fold_h(line, it) }
    }
    if (!h_foldable.isEmpty()) {
        return h_foldable[0] + 1
    }

    var v_foldable = List(lines.size - 1) { it }.filter { it != ignore / 100 - 1 }
    for (x in 0 ..< lines[0].length) {
        v_foldable = v_foldable.filter { may_fold_v(lines, it, x) }
    }
    if (!v_foldable.isEmpty()) {
        return (v_foldable[0] + 1) * 100
    }
    return -1
}

fun flipp(c: Char): Char {
    if (c == '.') {
        return '#'
    }
    return '.'
}

fun bit_flipped(lines: List<String>) = sequence {
    for (y in 0 ..< lines.size) {
        for (x in 0 ..< lines[y].length) {
            yield(
                    lines.subList(0, y) +
                            listOf(
                                    lines[y].substring(0, x) +
                                            flipp(lines[y][x]) +
                                            lines[y].substring(x + 1, lines[y].length)
                            ) +
                            lines.subList(y + 1, lines.size)
            )
        }
    }
}

fun main() {
    var lines = File("input.txt").readLines()
    var sum = 0
    var start = 0

    for (i in 0 ..< lines.size) {
        if (lines[i].isBlank()) {
            var v = 0
            val default_val = reflection_value(lines.subList(start, i))
            for (permutation in bit_flipped(lines.subList(start, i))) {
                v = reflection_value(permutation, default_val)
                if (v != -1) {
                    break
                }
            }

            sum += v
            start = i + 1
        }
    }
    var v = 0
    val default_val = reflection_value(lines.subList(start, lines.size))
    for (permutation in bit_flipped(lines.subList(start, lines.size))) {
        v = reflection_value(permutation, default_val)
        if (v != -1) {
            break
        }
    }

    sum += v
    println(sum)
}
