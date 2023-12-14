import java.io.File
import java.math.BigInteger

fun spring_combos_damaged(
        pos: Int,
        group_size: Int,
        group_index: Int,
        conditions: String,
        groups: List<Int>
): ULong {
    if (group_size > 0 && group_size != groups[group_index]) {
        return 0UL
    }
    if (group_size > 0) {
        return spring_combos(pos + 1, 0, group_index + 1, conditions, groups)
    }
    return spring_combos(pos + 1, 0, group_index, conditions, groups)
}

fun spring_combos(
        pos: Int,
        group_size: Int,
        group_index: Int,
        conditions: String,
        groups: List<Int>
): ULong {
    if (pos >= conditions.length &&
                    group_index == groups.size - 1 &&
                    group_size == groups[group_index]
    ) {
        return 1UL
    }
    if (pos >= conditions.length && group_size == 0 && groups.size == group_index) {
        return 1UL
    }
    if (pos >= conditions.length) {
        return 0UL
    }

    if (group_size > 0 && groups.size <= group_index) {
        return 0UL
    }
    if (group_size > 0 && groups[group_index] < group_size) {
        return 0UL
    }

    if (conditions[pos] == '.') {
        return spring_combos_damaged(pos, group_size, group_index, conditions, groups)
    }

    if (conditions[pos] == '#') {
        return spring_combos(pos + 1, group_size + 1, group_index, conditions, groups)
    }

    return spring_combos_damaged(pos, group_size, group_index, conditions, groups) +
            spring_combos(pos + 1, group_size + 1, group_index, conditions, groups)
}

fun main() {
    var lines = File("input.txt").readLines()
    var count = BigInteger("0")
    var i = 0
    for (line in lines) {
        var conditions = line.split(" ")[0]
        var groups = line.split(" ")[1].split(",").map { Integer.parseInt(it) }
        println(i++)
        count =
                count.add(
                        BigInteger(
                                spring_combos(
                                                0,
                                                0,
                                                0,
                                                conditions + ('?' + conditions).repeat(4),
                                                MutableList(groups.size * 5) {
                                                    groups[it % groups.size]
                                                }
                                        )
                                        .toString()
                        )
                )
        println(count)
    }
    println(count)
}
