package com.example.transcore.presentation.uiState

import com.example.transcore.data.models.Language
import com.example.transcore.data.models.TranslationResult

data class TranslatorUiState(
    val sourceText: String = "",
    val translatedText: String = "",
    val sourceLanguage: Language = Language("auto", "Select", "Detect"),
    val targetLanguage: Language = Language("en", "English", "English"),
    val availableLanguages: List<Language> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val lastTranslation: TranslationResult? = null
)
