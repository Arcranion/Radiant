package arcranion.radiant.internationalization

import arcranion.radiant.internationalization.loader.RadiantI18nLoader
import arcranion.radiant.util.reflection.Reflect
import org.jline.reader.Candidate
import java.io.InputStream
import java.util.Locale
import kotlin.reflect.KClass
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind

open class RadiantI18nManager(
    val loader: RadiantI18nLoader
) {

    private val data = mutableMapOf<Locale, MutableList<RadiantI18nData>>()

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

    fun loadCandidateFromResource(
        resourceName: String,
        loader: RadiantI18nLoader = this.loader
    ) {
        val callerClass = Reflect.getCallerClass()
        loadCandidateFromResource(callerClass, resourceName, loader)
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

}