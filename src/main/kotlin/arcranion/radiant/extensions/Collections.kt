package arcranion.radiant.extensions

fun <T: Pair<*, *>> Collection<T>.mapFirst() = map { it.first }

fun <T: Pair<*, *>> Collection<T>.mapSecond() = map { it.first }

fun <T, V> Collection<T>.mapAsPairFirst(secondValue: V) = map { it to secondValue }
fun <T> Collection<T>.mapAsPairFirst() = mapAsPairFirst(null)
fun <T, V> Collection<T>.mapAsPairFirst(secondProvider: (T) -> V) = map { it to secondProvider(it) }

fun <T, V> Collection<T>.mapAsPairSecond(firstValue: V) = map { firstValue to it }
fun <T> Collection<T>.mapAsPairSecond() = mapAsPairSecond(null)
fun <T, V> Collection<T>.mapAsPairSecond(firstProvider: (T) -> V) = map { firstProvider(it) to it }