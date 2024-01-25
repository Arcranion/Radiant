package arcranion.radiant.internationalization

class RadiantI18nFormatException(
    message: String
): Exception(
    "The data is in wrong format: $message"
)