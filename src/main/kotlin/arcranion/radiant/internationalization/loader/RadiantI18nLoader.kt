package arcranion.radiant.internationalization.loader

import arcranion.radiant.internationalization.RadiantI18nData
import java.io.InputStream
import kotlin.reflect.KClass

sealed interface RadiantI18nLoader {

    fun load(stream: InputStream): RadiantI18nData

    fun loadFromResource(clazz: Class<*>, name: String): RadiantI18nData {
        val stream = clazz.getResourceAsStream(name)
            ?: throw RuntimeException("Resource \"$name\" not found at class ${clazz.canonicalName ?: clazz.simpleName}")

        return this.load(stream)
    }

    fun loadFromResource(clazz: KClass<*>, name: String) =
        loadFromResource(clazz.java, name)

}