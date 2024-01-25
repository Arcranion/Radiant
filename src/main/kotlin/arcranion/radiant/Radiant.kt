package arcranion.radiant

import arcranion.radiant.listeners.PlayerListener
import org.bukkit.Bukkit

object Radiant {

    var initialized = false
        private set

    fun initialize() {
        if(initialized) return
        initialized = true

        Bukkit.getPluginManager().registerEvents(PlayerListener(), RadiantPlugin)
    }

}