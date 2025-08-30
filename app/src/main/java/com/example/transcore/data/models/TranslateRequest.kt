package com.example.transcore.data.models

import com.example.transcore.BuildConfig


data class TranslateRequest(
    val q: String,
    val source: String,
    val target: String,
    val format: String = "text",
    val api_key: String = BuildConfig.TRANSLATE_API_KEY
)