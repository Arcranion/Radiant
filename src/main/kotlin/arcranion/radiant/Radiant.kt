package arcranion.radiant

import arcranion.radiant.listeners.PlayerListener
import arcranion.radiant.logging.logger
import org.bukkit.Bukkit
import org.slf4j.LoggerFactory

object Radiant {

    internal val logger = logger("Radiant")

    var initialized = false
        private set

    fun initialize() {
        if(initialized) return
        initialized = true

        Bukkit.getPluginManager().registerEvents(PlayerListener(), RadiantPlugin)
    }

}