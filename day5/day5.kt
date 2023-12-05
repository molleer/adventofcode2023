import java.io.File
import kotlin.math.max
import kotlin.math.min

fun in_range(range: Triple<Long, Long, Long>, seed: Long): Boolean {
    return seed >= range.second && seed < range.second + range.third
}

fun in_range(range: Triple<Long, Long, Long>, seed: Pair<Long, Long>): List<Pair<Long, Long>> {
    if (seed.second < range.second) {
        return listOf(seed)
    }
    if (seed.first > range.second + range.third - 1) {
        return listOf(seed)
    }
    var first = max(seed.first, range.second)
    var second = min(seed.second, range.second + range.third - 1)

    var ranges = ArrayList<Pair<Long, Long>>()
    ranges.add(Pair(first - range.second + range.first, second - range.second + range.first))
    if (seed.first < first) {
        ranges.add(Pair(seed.first, first - 1))
    }
    if (seed.second > second) {
        ranges.add(Pair(second + 1, seed.second))
    }
    return ranges
}

fun apply_cat(
        category: List<Triple<Long, Long, Long>>,
        seed: Pair<Long, Long>
): List<Pair<Long, Long>> {
    var res = HashSet<Pair<Long, Long>>()
    for (range in category) {
        var new_seed = in_range(range, seed)
        res.addAll(new_seed)
    }
    if (res.size == 1 && res.contains(seed)) {
        return listOf(seed)
    }
    return res.filter { !it.equals(seed) }
}

fun merge_ranges(ranges: List<Pair<Long, Long>>): List<Pair<Long, Long>> {
    if (ranges.isEmpty()) {
        return ranges
    }
    var new_ranges = ArrayList<Pair<Long, Long>>()
    var sorted =
            ranges.sortedWith { a, b ->
                if (a.first == b.first) a.second.compareTo(b.second) else a.first.compareTo(b.first)
            }
    new_ranges.add(ranges.first())
    for (i in 1 ..< sorted.size) {
        var last = new_ranges.last()
        if (sorted[i].first > last.second) {
            new_ranges.add(sorted[i])
        } else {
            last = Pair(last.first, sorted[i].second)
            new_ranges.set(new_ranges.size - 1, last)
        }
    }
    return new_ranges
}

fun apply_cat(category: List<Triple<Long, Long, Long>>, seed: Long): Long {
    for (range in category) {
        if (in_range(range, seed)) {
            return seed - range.second + range.first
        }
    }

    return seed
}

fun main() {
    var lines = File("input.txt").readLines()

    var seeds = lines[0].removePrefix("seeds: ").split(" ").map { it.toLong() }
    var categories = ArrayList<ArrayList<Triple<Long, Long, Long>>>()
    for (i in 2 ..< lines.size) {
        if (lines[i].contains("map")) {
            categories.add(ArrayList())
            continue
        }
        if (lines[i].equals("")) {
            continue
        }

        var nums = lines[i].split(" ").map { it.toLong() }
        categories.last().add(Triple(nums[0], nums[1], nums[2]))
    }

    var real_seeds: MutableSet<Pair<Long, Long>> = HashSet<Pair<Long, Long>>()
    for (i in 0 ..< seeds.size step 2) {
        real_seeds.add(Pair(seeds[i], seeds[i] + seeds[i + 1] - 1))
    }

    println(real_seeds)

    for (cat in categories) {
        println(cat)
        real_seeds = real_seeds.map { apply_cat(cat, it) }.flatten().toMutableSet()
        println(real_seeds)
        println()
    }
    print(real_seeds.minBy { it.first }.first)
}
