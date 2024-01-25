package arcranion.radiant

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.event.player.PlayerEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

object RadiantCache {

    private val _offlinePlayers = mutableSetOf<OfflinePlayer>()

    val offlinePlayers: Set<OfflinePlayer>
        get() = _offlinePlayers

    val offlinePlayersUUID
        get() = offlinePlayers.map { it.uniqueId }

    val offlinePlayersName
        get() = offlinePlayers.mapNotNull { it.name }

    fun cacheAll() {
        _offlinePlayers.clear()
        _offlinePlayers.addAll(Bukkit.getOfflinePlayers())
    }

    fun updateOfflinePlayers(event: PlayerEvent) {
        val player = event.player

        if(player.uniqueId !in offlinePlayersUUID) {
            _offlinePlayers += player
        }
    }

}