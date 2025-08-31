package com.example.transcore.presentation.ui.history.domain.model

data class TranslationHistory(
    val id: Int = 0,
    val inputText: String,
    val translatedText: String,
    val sourceLang: String,
    val targetLang: String,
    val timestamp: Long
)
