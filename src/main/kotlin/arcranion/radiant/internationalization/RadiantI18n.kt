package arcranion.radiant.internationalization

import arcranion.radiant.internationalization.loader.RadiantI18nLoader
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import java.io.InputStream
import java.util.Locale
import kotlin.reflect.KClass

open class RadiantI18n(
    val loader: RadiantI18nLoader
) {

    private val data = mutableMapOf<Locale, MutableList<RadiantI18nData>>()

    open val availableLocales
        get() = data.keys.toList()

    open val fallbackLocale: Locale = Locale.ENGLISH
    open val consoleLocale: Locale
        get() = fallbackLocale

    fun loadCandidate(stream: InputStream, loader: RadiantI18nLoader = this.loader) {
        addCandidate(loader.load(stream))
    }

    fun loadCandidateFromResource(
        resourceClass: Class<*>,
        resourceName: String,
        loader: RadiantI18nLoader = this.loader
    ) {
        addCandidate(loader.loadFromResource(resourceClass, resourceName))
    }

    fun loadCandidateFromResource(
        resourceClass: KClass<*>,
        resourceName: String,
        loader: RadiantI18nLoader = this.loader
    ) {
        addCandidate(loader.loadFromResource(resourceClass, resourceName))
    }

    inline fun <reified T> loadCandidateFromResource(
        resourceName: String,
        loader: RadiantI18nLoader = this.loader
    ) {
        loadCandidateFromResource(T::class, resourceName, loader)
    }

    fun getCandidates(locale: Locale): List<RadiantI18nData>? {
        return data[locale]?.toList()
    }

    fun addCandidate(candidate: RadiantI18nData, locale: Locale = candidate.locale) {
        val candidates = data.getOrPut(locale) { mutableListOf() }
        candidates.add(candidate)
    }

    fun removeLocale(locale: Locale) {
        data.remove(locale)
    }

    fun removeCandidate(candidate: RadiantI18nData, locale: Locale = candidate.locale) {
        data[locale]?.remove(candidate)
    }

    fun getData(): Map<Locale, MutableList<RadiantI18nData>> {
        return this.data
    }

    fun getOr(locale: Locale, name: String, default: String): String {
        val candidates = data[locale]
            ?: return default

        for (candidate in candidates) {
            val value = candidate[name]
                ?: continue

            return value
        }

        return default
    }

    fun getOrNull(locale: Locale, name: String): String? {
        val candidates = data[locale]
            ?: return null

        for (candidate in candidates) {
            val value = candidate[name]
                ?: continue

            return value
        }

        return null
    }

    operator fun get(locale: Locale, name: String): String {
        val candidates = data[locale]
            ?: throw RuntimeException("Locale $locale not found")

        for (candidate in candidates) {
            val value = candidate[name]
                ?: continue

            return value
        }

        throw RuntimeException("Key $name not found in $locale")
    }

    fun matchLocale(sender: CommandSender): Locale {
        val locale = when(sender) {
            is Player -> sender.locale()
            is ConsoleCommandSender -> this.consoleLocale
            else -> this.fallbackLocale
        }

        if(locale in availableLocales) return locale

        // Return if there's matching language, but not for country
        availableLocales
            .find { it.language == locale.language }
            ?.let { return it }

        // Return fallback locale
        return fallbackLocale
    }

}