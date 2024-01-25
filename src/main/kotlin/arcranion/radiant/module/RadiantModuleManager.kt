package arcranion.radiant.module

open class RadiantModuleManager {
    val rootModule = RadiantRootModule()

    private val modules = RadiantModuleList(rootModule)

    fun add(module: RadiantModule, enableAfter: Boolean = false) {
        modules += module
        if(enableAfter) module.enable()
    }

    fun remove(module: RadiantModule, disableBefore: Boolean = false) {
        if(disableBefore) module.disable()
        modules -= module
    }

    fun list(): List<RadiantModule> {
        return modules
    }

    fun find(name: String, filter: (RadiantModule) -> Boolean): RadiantModule? {
        return modules.find { it.name == name && filter(it) }
    }

    fun find(name: String): RadiantModule? {
        return modules.find { it.name == name }
    }

    fun enable(name: String) {
        find(name)?.enable()
    }

    fun disable(name: String) {
        find(name) { it !is RequiredRadiantModule }?.disable()
    }

    fun enableAll() {
        modules.filter { !it.enabled }.forEach { it.enable() }
    }

    fun disableAll(force: Boolean = false) {
        if(force) modules.forEach { it.disable() }
        modules.filter { it !is RequiredRadiantModule }.forEach { it.disable() }
    }



}