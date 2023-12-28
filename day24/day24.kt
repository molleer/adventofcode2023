import java.io.File
import kotlin.math.abs

fun cross(a: Pair<List<Double>, List<Double>>, b: Pair<List<Double>, List<Double>>): Boolean {
    val k1 = a.second[1] / a.second[0]
    val k2 = b.second[1] / b.second[0]
    val m1 = a.first[1] - k1 * a.first[0]
    val m2 = b.first[1] - k2 * b.first[0]
    if (abs(k1 - k2) < 0.00000000000001) {
        println("Parallell!")
        return m1 == m2
    }
    val x = (m2 - m1) / (k1 - k2)
    val y = k1 * x + m1
    val max = 400_000_000_000_000L
    val min = 200_000_000_000_000L

    val dot1 = (x - a.first[0]) * a.second[0] + (y - a.first[1]) * a.second[1]
    val dot2 = (x - b.first[0]) * b.second[0] + (y - b.first[1]) * b.second[1]

    println("($x, $y)")
    return x >= min && x <= max && y >= min && y <= max && dot1 > 0 && dot2 > 0
}

fun main() {
    var lines = File("input.txt").readLines()
    var hails = ArrayList<Pair<List<Double>, List<Double>>>()

    for (line in lines) {
        var split = line.split(" @ ")
        hails.add(
                Pair(
                        split[0].split(", ").map { it.trim().toDouble() },
                        split[1].split(", ").map { it.trim().toDouble() }
                )
        )
    }

    var count = 0

    for (i in 0 ..< hails.size) {
        for (j in i + 1 ..< hails.size) {
            if (cross(hails[i], hails[j])) {
                println("Ok!")
                count++
            }
        }
    }
    println(count)
}
