package arcranion.radiant.extensions.file

import java.io.File

fun File.createAnyway() {
    if(!parentFile.exists()) parentFile.mkdirs()
    createNewFile()
}

fun File.create(force: Boolean = true): Boolean {
    if(this.exists() && !force) return false
    if(!parentFile.exists()) parentFile.mkdirs()
    createNewFile()
    return true
}

fun File.createWith(content: String) {
    if(!this.exists()) {
        createAnyway()
        this.writeText(content)
    }
}

inline fun File.createWith(contentProvider: () -> String) {
    if (!this.exists()) {
        createAnyway()
        this.writeText(contentProvider())
    }
}

inline fun <T : Any> File.createThen(then: File.() -> T): T? {
    if(!this.exists()) {
        createAnyway()
        return then()
    }

    return null
}

inline fun File.createThen(then: File.() -> Unit){
    if(!this.exists()) {
        createAnyway()
        then()
    }
}