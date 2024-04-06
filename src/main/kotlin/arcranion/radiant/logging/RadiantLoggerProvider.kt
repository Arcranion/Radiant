package arcranion.radiant.logging

import arcranion.radiant.util.reflection.Reflect
import org.slf4j.Logger
import org.slf4j.LoggerFactory

typealias ProviderType = (String) -> Logger

object RadiantLoggerProvider {

    private var provider: ProviderType = LoggerFactory::getLogger

    fun resetProvider() {
        provider = LoggerFactory::getLogger
    }

    fun setProvider(provider: ProviderType) {
        this.provider = provider
    }

    fun getProvider(): ProviderType {
        return this.provider
    }

    fun provide(name: String): Logger {
        return provider(name)
    }

}

fun logger(name: String) =
    RadiantLoggerProvider.provide(name)

fun logger() =
    logger(Reflect.getCallerClass().name)