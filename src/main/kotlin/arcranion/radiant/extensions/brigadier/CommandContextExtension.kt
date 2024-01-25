package arcranion.radiant.extensions.brigadier

import arcranion.radiant.extensions.bukkit.locale
import arcranion.radiant.module.RadiantModule
import com.mojang.brigadier.context.CommandContext
import net.kyori.adventure.audience.MessageType
import net.kyori.adventure.identity.Identity
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.minecraft.commands.CommandSource
import net.minecraft.commands.CommandSourceStack
import org.bukkit.command.CommandSender
import java.text.MessageFormat

private val moduleRegistry = mutableMapOf<CommandContext<*>, RadiantModule>()

internal fun CommandContext<*>.beginModule(module: RadiantModule) {
    moduleRegistry[this] = module
}

internal fun CommandContext<*>.endModule() {
    moduleRegistry.remove(this)
}

val CommandContext<*>.assignedRadiantModule: RadiantModule?
    get() = moduleRegistry[this]

val CommandContext<CommandSourceStack>.sender: CommandSender
    get() = this.source.bukkitSender

fun CommandContext<CommandSourceStack>.translate(key: String, vararg args: Any?): String {
    val i18n = this.assignedRadiantModule!!.i18n
    val locale = sender.locale
    val translation = i18n.getOr(locale, key, "$$key$")

    val argsString = args.map(Any?::toString)

    val formatted = MessageFormat.format(translation, *argsString.toTypedArray())

    return formatted
}

fun CommandContext<CommandSourceStack>.sendMessage(message: String) = this.sender.sendMessage(message)
fun CommandContext<CommandSourceStack>.sendMessage(vararg message: String) = this.sender.sendMessage(*message)
fun CommandContext<CommandSourceStack>.sendRichMessage(message: String) = this.sender.sendRichMessage(message)
fun CommandContext<CommandSourceStack>.sendRichMessage(message: String, vararg tagResolver: TagResolver) = this.sender.sendRichMessage(message, *tagResolver)
fun CommandContext<CommandSourceStack>.sendPlainMessage(message: String) = this.sender.sendPlainMessage(message)

fun CommandContext<CommandSourceStack>.sendTranslated(key: String, vararg args: Any?) = sendMessage(translate(key, *args))
fun CommandContext<CommandSourceStack>.sendRichTranslated(key: String, vararg args: Any?) = sendRichMessage(translate(key, *args))
fun CommandContext<CommandSourceStack>.sendPlainTranslated(key: String, vararg args: Any?) = sendPlainMessage(translate(key, *args))