package arcranion.radiant.extensions.brigadier

import com.mojang.brigadier.LiteralMessage
import com.mojang.brigadier.Message
import com.mojang.brigadier.suggestion.SuggestionsBuilder

val SuggestionsBuilder.remainingSplit
    get() = remaining.split(" ")

fun SuggestionsBuilder.suggestAll(list: List<String>) {
    list.forEach(::suggest)
}

@JvmName("suggestAllTooltip")
fun SuggestionsBuilder.suggestAll(list: List<Pair<String, Message?>>) {
    list.forEach { suggest(it.first, it.second) }
}

@JvmName("suggestAllStringTooltip")
fun SuggestionsBuilder.suggestAll(list: List<Pair<String, String?>>) {
    list.forEach { suggest(it.first, it.second?.let { s -> LiteralMessage(s) }) }
}

@JvmName("suggestAllInt")
fun SuggestionsBuilder.suggestAll(list: List<Int>) {
    list.forEach(::suggest)
}

@JvmName("suggestAllIntTooltip")
fun SuggestionsBuilder.suggestAll(list: List<Pair<Int, Message?>>) {
    list.forEach { suggest(it.first, it.second) }
}

@JvmName("suggestAllIntStringTooltip")
fun SuggestionsBuilder.suggestAll(list: List<Pair<Int, String?>>) {
    list.forEach { suggest(it.first, it.second?.let { s -> LiteralMessage(s) }) }
}