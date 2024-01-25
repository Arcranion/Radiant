package arcranion.radiant.platform.bukkit

import com.mojang.brigadier.CommandDispatcher
import net.minecraft.commands.CommandSourceStack
import org.bukkit.Server
import org.bukkit.craftbukkit.v1_20_R3.CraftServer

object BukkitCommandDispatcherProvider {

    private val dispatcherCache = mutableMapOf<Server, CommandDispatcher<CommandSourceStack>>()

    fun getDispatcherOrNull(server: Server): CommandDispatcher<CommandSourceStack>? {
        return when(server) {
            is CraftServer ->
                server.server.vanillaCommandDispatcher.dispatcher

            else ->
                null
        }
    }

    fun getDispatcher(server: Server): CommandDispatcher<CommandSourceStack> {
        when(val dispatcher = getDispatcherOrNull(server)) {
            null ->
                throw RuntimeException("Failed to resolve command dispatcher: Unsupported server type")
            else ->
                return dispatcher
        }
    }

    fun getDispatcherCached(server: Server): CommandDispatcher<CommandSourceStack> {
        return dispatcherCache.getOrPut(server) { getDispatcher(server) }
    }

    fun getDispatcherCachedOrNull(server: Server): CommandDispatcher<CommandSourceStack>? {
        return dispatcherCache[server] ?: getDispatcherOrNull(server)
    }

}

val Server.commandDispatcher
    get() = BukkitCommandDispatcherProvider.getDispatcherCached(this)

val Server.commandDispatcherOrNull
    get() = BukkitCommandDispatcherProvider.getDispatcherCachedOrNull(this)