@file:Suppress("DEPRECATION", "removal")

package arcranion.radiant

import io.papermc.paper.plugin.configuration.PluginMeta
import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.generator.BiomeProvider
import org.bukkit.generator.ChunkGenerator
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginBase
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.PluginLoader
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.InputStream
import java.lang.UnsupportedOperationException
import java.util.logging.Logger

object RadiantPlugin: Plugin {
    override fun onTabComplete(
        p0: CommandSender,
        p1: Command,
        p2: String,
        p3: Array<out String>?
    ): MutableList<String>? {
        return null
    }

    override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>?): Boolean {
        return false
    }

    override fun getDataFolder(): File {
        return File(Bukkit.getPluginsFolder(), "Radiant")
    }

    override fun getDescription(): PluginDescriptionFile {
        throw UnsupportedOperationException()
    }

    override fun getPluginMeta(): PluginMeta {
        throw UnsupportedOperationException()
    }

    override fun getConfig(): FileConfiguration {
        throw UnsupportedOperationException()
    }

    override fun getResource(p0: String): InputStream? {
        return null
    }

    override fun saveConfig() {

    }

    override fun saveDefaultConfig() {

    }

    override fun saveResource(p0: String, p1: Boolean) {

    }

    override fun reloadConfig() {

    }

    @Deprecated("Deprecated in Java")
    @Suppress("removal")
    override fun getPluginLoader(): PluginLoader {
        throw UnsupportedOperationException()
    }

    override fun getServer(): Server {
        return Bukkit.getServer()
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun onDisable() {

    }

    override fun onLoad() {

    }

    override fun onEnable() {

    }

    override fun isNaggable(): Boolean {
        return false
    }

    override fun setNaggable(p0: Boolean) {

    }

    override fun getDefaultWorldGenerator(p0: String, p1: String?): ChunkGenerator? {
        return null
    }

    override fun getDefaultBiomeProvider(p0: String, p1: String?): BiomeProvider? {
        return null
    }

    override fun getLogger(): Logger {
        return Bukkit.getLogger()
    }

    override fun getName(): String {
        return "Radiant"
    }

}