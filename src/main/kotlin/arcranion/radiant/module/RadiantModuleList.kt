package arcranion.radiant.module

class RadiantModuleList(
    val module: RadiantModule
): ArrayList<RadiantModule>() {

    override fun add(element: RadiantModule): Boolean {
        element.parent = module
        return super.add(element)
    }

    fun initializeAll() {
        this.forEach(RadiantModule::initialize)
    }

}