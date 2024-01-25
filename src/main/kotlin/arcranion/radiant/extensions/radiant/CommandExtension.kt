package arcranion.radiant.extensions.radiant

import arcranion.radiant.module.RadiantModule
import org.bukkit.command.Command
import java.util.*

val RadiantModule.commandRegistry by lazy { mutableMapOf<String, Pair<Runnable, Runnable>>() }

fun RadiantModule.registerCommand(command: Command) {
    val namespace = plugin.name.lowercase(Locale.ENGLISH)
    val fullName = "${namespace}:${command.name}"

    plugin.server.commandMap.register(namespace, command)

    commandRegistry[fullName] = Runnable {
        plugin.server.commandMap.register(namespace, command)
    } to Runnable {
        plugin.server.commandMap.knownCommands.remove(name)
    }
}

fun RadiantModule.enableCommand(name: String) {
    val namespace = plugin.name.lowercase(Locale.ENGLISH)
    val fullName = "${namespace}:${name}"

    if(fullName !in commandRegistry)

    commandRegistry[fullName]?.first?.run()
}

fun RadiantModule.disableCommand(name: String) {
    val namespace = plugin.name.lowercase(Locale.ENGLISH)
    val fullName = "${namespace}:${name}"

    if(fullName !in commandRegistry)
        return

    commandRegistry[fullName]?.second?.run()
}

fun RadiantModule.enableAllCommands() {
    commandRegistry.forEach {
        enableCommand(it.key)
    }
}

fun RadiantModule.disableAllCommands() {
    commandRegistry.forEach {
        disableCommand(it.key)
    }
}