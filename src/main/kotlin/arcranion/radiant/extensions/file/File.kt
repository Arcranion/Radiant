package arcranion.radiant.extensions.file

import java.io.File

fun File.create() {
    if(!parentFile.exists()) parentFile.mkdirs()
    createNewFile()
}

fun File.createIfNotExists() {
    if(!this.exists()) create()
}