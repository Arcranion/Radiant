package arcranion.radiant.internationalization.loader

import arcranion.radiant.internationalization.RadiantI18nData
import arcranion.radiant.internationalization.RadiantI18nFormatException
import arcranion.radiant.logging.logger
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.*
import org.koin.core.component.getScopeName
import java.io.InputStream
import java.util.*

open class RadiantI18nJsonLoader(
    val json: Json = Json
): RadiantI18nLoader {

    private val logger = logger("RadiantI18nJsonLoader")

    @OptIn(ExperimentalSerializationApi::class)
    override fun load(stream: InputStream): RadiantI18nData {
        val data = json.decodeFromStream<JsonObject>(stream)

        val localeElement = data["\$locale$"]
            ?: throw RadiantI18nFormatException("Json data must contain key \"\$locale$\"")
        if(localeElement !is JsonPrimitive || !localeElement.isString)
            throw RadiantI18nFormatException("Value of \"\$locale$\" is not a String")

        val localeString = localeElement.content

        val locale = Locale.forLanguageTag(localeString)

        val map = mutableMapOf<String, String>()

        fun recurse(domain: Array<String>, obj: JsonObject) {
            for((key, value) in obj) {
                val currentDomain = domain + key
                val currentDomainString = currentDomain.joinToString(".")

                when(value) {
                    is JsonPrimitive -> {
                        if(!value.isString) logger.warn("JsonPrimitive at ${currentDomainString} is not a String, casting")

                        val content = value.contentOrNull
                        if(content == null) logger.warn("JsonPrimitive at ${currentDomainString} is null")

                        map[currentDomainString] = content.toString()
                    }
                    is JsonObject -> {
                        recurse(currentDomain, value)
                    }
                    else -> {
                        logger.warn("JsonElement at ${currentDomainString} is not a JsonPrimitive or JsonObject, skipping")
                        continue
                    }
                }
            }
        }

        recurse(emptyArray(), data)

        return RadiantI18nData(
            locale = locale,
            initialData = map
        )
    }

    companion object: RadiantI18nJsonLoader()
}