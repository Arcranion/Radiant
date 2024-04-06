package arcranion.radiant.extensions.serialization

import arcranion.radiant.extensions.file.create
import arcranion.radiant.extensions.file.createThen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.contracts.ExperimentalContracts

inline fun <reified T> Json.readConfiguration(file: File, crossinline default: () -> T): T {
    file.createThen {
        val instance = default()

        writeText(encodeToString(instance))
        return instance
    }

    return decodeFromString(file.readText())
}

inline fun <reified T> Json.writeConfiguration(file: File, data: T) {
    val encoded = encodeToString(data)

    file.create()
    file.writeText(encoded)
}

inline fun <reified T> Json.writeConfiguration(file: File, data: () -> T) {
    val encoded = encodeToString(data())

    file.create()
    file.writeText(encoded)
}

inline fun <reified T, R> Json.readConfigurationThen(file: File, crossinline default: () -> T, then: T.() -> R): R {
    return readConfiguration(file, default).run(then)
}