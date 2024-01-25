package arcranion.radiant.module

import arcranion.radiant.extensions.radiant.disableAllCommands
import arcranion.radiant.extensions.radiant.enableAllCommands
import arcranion.radiant.internationalization.RadiantI18nManager
import org.bukkit.plugin.Plugin
import org.koin.core.component.KoinComponent

abstract class RadiantModule(
    open var parent: RadiantModule? = null,
): KoinComponent {
    abstract val name: String
    open val displayName: String by lazy { name }
    open val description: String = "No description provided"

    open val plugin: Plugin
        get() = parent?.plugin!!

    open val i18n: RadiantI18nManager
        get() = parent?.i18n!!

    val submodules = RadiantModuleList(this)

    var enabled = false
        private set

    private var initialized = false

    fun initialize() {
        onInitialize()
        submodules.initializeAll()
    }

    open fun enable() {
        if(enabled) return
        if(!initialized) initialize()

        onEnable()

        enableAllCommands()

        submodules.forEach(RadiantModule::enable)

        enabled = true
    }

    open fun disable() {
        if(!enabled) return

        onDisable()

        disableAllCommands()

        submodules.forEach(RadiantModule::disable)

        enabled = false
    }

    protected open fun onInitialize() {}
    protected open fun onEnable() {}
    protected open fun onDisable() {}
}

