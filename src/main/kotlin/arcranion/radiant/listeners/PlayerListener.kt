package arcranion.radiant.listeners

import arcranion.radiant.RadiantCache
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerListener: Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        // Process offlinePlayers cache
        RadiantCache.updateOfflinePlayers(event)
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        // Process offlinePlayers cache
        RadiantCache.updateOfflinePlayers(event)
    }

}