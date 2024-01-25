package arcranion.radiant.extensions.reflection

import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

@Suppress("UNCHECKED_CAST")
fun <T, C: Any> KClass<C>.usePrivateField(name: String, obj: C, body: (T) -> Unit) {
    val field = declaredMemberProperties.find { it.name == name }!!
    field.isAccessible = true
    body(field.get(obj)!! as T)
    field.isAccessible = false
}

@Suppress("UNCHECKED_CAST")
fun <R: Any?> Any.privateFieldValue(name: String): R {
    val targetField = this::class.declaredMemberProperties.find { it.name == name }!! as KProperty1<Any, R>
    targetField.isAccessible = true
    return targetField.get(this)
}