package arcranion.radiant.internationalization

import java.util.Locale

class RadiantI18nData(
    val locale: Locale,
    initialData: Map<String, String> = mapOf()
): Map<String, String> by initialData.toMap() {

}