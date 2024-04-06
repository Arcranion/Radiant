package arcranion.radiant.internationalization

import java.text.MessageFormat
import java.util.Locale

data class TranslatableString(
    val key: String,
    val args: List<Any?>
): CharSequence by key {
    constructor(content: String, vararg args: Any?): this(content, args.toList())

    fun translate(i18n: RadiantI18n, locale: Locale): String {
        val translation = i18n.getOr(locale, key, "$$key$")

        val argsString = args.map {
            if(it is TranslatableString) it.translate(i18n, locale)
            else it.toString()
        }

        val formatted = MessageFormat.format(translation, *argsString.toTypedArray())

        return formatted
    }
}

fun translatable(key: String, vararg args: Any?): TranslatableString {
    return TranslatableString(key, *args)
}