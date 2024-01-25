package arcranion.radiant.extensions.bukkit

import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import java.text.MessageFormat
import java.util.Locale

fun CommandSender.localeOr(default: Locale): Locale {
    return when(this) {
        is Player -> this.locale()
        else -> default
    }
}

val CommandSender.locale: Locale
    get() = this.localeOr(Locale.ENGLISH)