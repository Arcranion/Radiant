package arcranion.radiant.internationalization.loader

import arcranion.radiant.internationalization.RadiantI18nData
import arcranion.radiant.internationalization.RadiantI18nFormatException
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.InputStream
import java.util.*

open class RadiantI18nJsonLoader(
    val json: Json = Json
): RadiantI18nLoader {
    @OptIn(ExperimentalSerializationApi::class)
    override fun load(stream: InputStream): RadiantI18nData {
        val data = json.decodeFromStream<Map<String, String>>(stream)

        val localeString = data["\$locale$"]
            ?: throw RadiantI18nFormatException("Json data should contain key \"\$locale$\"")

        val locale = Locale.forLanguageTag(localeString)

        return RadiantI18nData(
            locale = locale,
            initialData = data
        )
    }

    companion object: RadiantI18nJsonLoader()
}