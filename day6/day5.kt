import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

fun dist(press_time: Int, limit: Int): Int {
    return press_time * limit - press_time * press_time
}

fun main() {
    var times = listOf(40, 82, 84, 92)
    var distances = listOf(233, 1011, 1110, 1487)
    var prod = 1

    for (i in 0 ..< times.size) {
        prod *= (0..times[i]).map { dist(it, times[i]) }.filter { it > distances[i] }.size
    }
    println(prod)

    var x = 40828492L
    var y = 233101111101487L
    print((floor(x / 2 + sqrt(x * x / 4.0 - y)) - ceil(x / 2 - sqrt(x * x / 4.0 - y)) + 1).toLong())
}
