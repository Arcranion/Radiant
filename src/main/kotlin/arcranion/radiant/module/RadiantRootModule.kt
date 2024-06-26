package arcranion.radiant.module

import arcranion.radiant.internationalization.RadiantI18n
import org.bukkit.plugin.Plugin
import org.koin.core.component.inject

class RadiantRootModule: RadiantModule() {

    override val name = "RootModule"

    override val plugin: Plugin by inject()

    override val i18n: RadiantI18n by inject()
}