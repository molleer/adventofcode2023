import java.io.File

// Function to return GCD of a and b
fun gcd(a: Long, b: Long): Long {
    if (a == 0L) return b
    return gcd(b % a, a)
}

// A simple method to evaluate
// Euler Totient Function
fun phi(n: Long): Long {
    var result = 1L
    for (i in 2 ..< n) if (gcd(i, n) == 1L) result++
    return result
}

fun pow_l(n: Long, expo: Long): Long {
    var res = 1L
    for (i in 0 ..< expo) {
        res *= n
    }
    return res
}

fun main() {
    var input = File("input.txt").readLines()
    var network = HashMap<String, Pair<String, String>>()
    for (i in 2 ..< input.size) {
        network.set(
                input[i].substring(0, 3),
                Pair(input[i].substring(7, 10), input[i].substring(12, 15))
        )
    }

    var positions = ArrayList(network.keys.filter { it.last() == 'A' })
    var visited = positions.map { ArrayList<Pair<Int, String>>() }
    visited.forEachIndexed { i, set -> set.add(Pair(0, positions[i])) }
    var not_done = (0 ..< positions.size).toList()

    var instructions = input[0]

    while (!not_done.isEmpty()) {
        for (i in 0 ..< instructions.length) {
            for (p in not_done) {
                if (instructions[i] == 'L') {
                    positions[p] = network.getOrDefault(positions[p], Pair("", "")).first
                } else {
                    positions[p] = network.getOrDefault(positions[p], Pair("", "")).second
                }
            }

            var done = not_done.filter { Pair(i, positions[it]) in visited[it] }
            not_done = not_done.filter { !(it in done) }
            for (p in not_done + done) {
                visited[p].add(Pair(i, positions[p]))
            }

            if (not_done.isEmpty()) {
                break
            }
        }
    }

    // Kinesiska restsatsen
    // x = <distance to first Z> mod <period of loop>
    /*

    */

    var distances =
            (0 ..< positions.size)
                    .map { visited[it].indexOf(visited[it].filter { it.second[2] == 'Z' }[0]) }
                    .map { it.toLong() }
    var periods =
            (0 ..< positions.size)
                    .map { visited[it].size - visited[it].indexOf(visited[it].last()) }
                    .map { it.toLong() }
    var N = periods.fold(1L) { a, b -> a * b }
    var b = periods.map { pow_l(N / it, phi(it) - 1) % it }

    println("Distances: $distances")
    println("Periods: $periods")
    println("N: $N")
    println("b: $b")

    var count = 0L
    for (i in 0 ..< positions.size) {
        count += (distances[i] * b[i] * (N / periods[i]))
    }
    println(count % N)
}
