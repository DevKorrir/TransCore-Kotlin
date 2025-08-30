package com.example.transcore.data.models

data class TranslateRequest(
    val q: String,
    val source: String,
    val target: String,
    val format: String = "text",
    val api_key: String = BuildConfig.TRANSLATE_API_KEY
)