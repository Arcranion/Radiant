package arcranion.radiant.module

import java.util.UUID

open class AnonymousRadiantModule: RadiantModule() {
    val id = UUID.randomUUID()

    override val name = "anonymous$${id}"
    override val displayName = "AnonymousRadiantModule$${id}"
    override val description = "Anonymous module"
}