package arcranion.radiant.extensions

fun Char.isNumeric(): Boolean {
    return this in '0'..'9'
}