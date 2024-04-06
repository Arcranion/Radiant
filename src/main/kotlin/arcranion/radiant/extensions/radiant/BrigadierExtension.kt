package arcranion.radiant.extensions.radiant

import arcranion.radiant.module.RadiantModule
import arcranion.radiant.platform.bukkit.commandDispatcher
import arcranion.radiant.util.brigadier.BrigadierLiteralArgumentBuilder
import arcranion.radiant.extensions.brigadier.register
import arcranion.radiant.extensions.brigadier.unregister
import arcranion.radiant.util.brigadier.BrigadierDsl
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.minecraft.commands.CommandSourceStack
import java.util.*

@BrigadierDsl
class BrigadierCommandsBuilder(
    val module: RadiantModule
) {
    fun command(name: String, setup: @BrigadierDsl BrigadierLiteralArgumentBuilder<CommandSourceStack>.() -> Unit) {
        val builder = BrigadierLiteralArgumentBuilder<CommandSourceStack>(
            LiteralArgumentBuilder.literal(name),
            module
        ).apply(setup).builder
        val build = module.plugin.server.commandDispatcher.register(builder)

        val namespace = module.plugin.name.lowercase(Locale.ENGLISH)
        val fullName = "${namespace}:${build.name}"

        module.commandRegistry[fullName] = Runnable {
            module.plugin.server.commandDispatcher.register(build)
        } to Runnable {
            module.plugin.server.commandDispatcher.unregister(build.name)
        }
    }
}

fun RadiantModule.brigadier(setup: @BrigadierDsl BrigadierCommandsBuilder.() -> Unit) {
    BrigadierCommandsBuilder(this).apply(setup)
}