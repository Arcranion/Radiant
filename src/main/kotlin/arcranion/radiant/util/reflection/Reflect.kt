package arcranion.radiant.util.reflection

import kotlin.reflect.jvm.jvmName

object Reflect {

    fun getCallerClass(): Class<*> {
        val stackTrace = Thread.currentThread().stackTrace
        val callerElement = stackTrace[2]
        val callerClassName = callerElement.className

        return Class.forName(callerClassName)
    }

    fun getCallerClassOrNull(): Class<*>? {
        val stackTrace = Thread.currentThread().stackTrace
        val callerElement = stackTrace[2]
        val callerClassName = callerElement.className

        return try {
            Class.forName(callerClassName)
        } catch (e: Exception) {
            null
        }
    }

}