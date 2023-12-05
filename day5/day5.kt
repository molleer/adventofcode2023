import java.io.File
import kotlin.math.max
import kotlin.math.min

fun apply_range(
        range: Triple<Long, Long, Long>,
        seed: Pair<Long, Long>
): Pair<List<Pair<Long, Long>>, Pair<Long, Long>> {
    if (seed.second < range.second) {
        return Pair(listOf(seed), Pair(1, 0))
    }
    if (seed.first > range.second + range.third - 1) {
        return Pair(listOf(seed), Pair(1, 0))
    }
    var first = max(seed.first, range.second)
    var second = min(seed.second, range.second + range.third - 1)

    var ranges = ArrayList<Pair<Long, Long>>()
    if (seed.first < first) {
        ranges.add(Pair(seed.first, first - 1))
    }
    if (seed.second > second) {
        ranges.add(Pair(second + 1, seed.second))
    }
    return Pair(
            ranges,
            Pair(first - range.second + range.first, second - range.second + range.first)
    )
}

fun apply_cat_range(
        category: List<Triple<Long, Long, Long>>,
        seed: Pair<Long, Long>
): List<Pair<Long, Long>> {
    if (category.isEmpty()) {
        return listOf(seed)
    }

    var applied_range = apply_range(category[0], seed)
    var res =
            applied_range
                    .first
                    .map { apply_cat_range(category.subList(1, category.size), it) }
                    .flatten()
                    .toMutableList()
    if (!applied_range.second.equals(Pair(1, 0))) {
        res.add(applied_range.second)
    }
    return res
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
    new_ranges.add(sorted.first())

    for (i in 1 ..< sorted.size) {
        var last = new_ranges.last()
        if (last.second < sorted[i].first) {
            new_ranges.add(sorted[i])
        } else {
            last = Pair(last.first, sorted[i].second)
            new_ranges.set(new_ranges.size - 1, last)
        }
    }
    return new_ranges
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

    for (cat in categories) {
        real_seeds = real_seeds.map { apply_cat_range(cat, it) }.flatten().toMutableSet()
    }
    print(real_seeds.filter { it.first <= it.second }.minBy { it.first }.first)
}
