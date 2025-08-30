package com.example.transcore.data.models

data class Language(
    val code: String,
    val name: String,
    val nativeName: String? = null
) {
    companion object {
        val AUTO_DETECT = Language("auto", "Auto Detect", "Auto Detect")

        // 100+ languages supported by Google Translate
        val SUPPORTED_LANGUAGES = listOf(
            Language("af", "Afrikaans", "Afrikaans"),
            Language("sq", "Albanian", "Shqip"),
        )
    }
}