package arcranion.radiant.util.reflection

import kotlin.reflect.jvm.jvmName

object Reflect {

    @Suppress("NOTHING_TO_INLINE")
    inline fun getCallerClass(): Class<*> {
        val stackTrace = Thread.currentThread().stackTrace
        val callerElement = stackTrace[1]
        val callerClassName = callerElement.className

        return Class.forName(callerClassName)
    }

    @Suppress("NOTHING_TO_INLINE")
    inline fun getCallerClassOrNull(): Class<*>? {
        val stackTrace = Thread.currentThread().stackTrace
        val callerElement = stackTrace[1]
        val callerClassName = callerElement.className

        return try {
            Class.forName(callerClassName)
        } catch (e: Exception) {
            null
        }
    }

}