package com.example.transcore.presentation.ui.history.domain.mapper

import com.example.transcore.presentation.ui.history.data.entity.TranslationHistoryEntity
import com.example.transcore.presentation.ui.history.domain.model.TranslationHistory


fun TranslationHistoryEntity.toDomain() = TranslationHistory(
    id = id,
    sourceText = sourceText,
    translatedText = translatedText,
    sourceLang = sourceLang,
    targetLang = targetLang,
    timestamp = timestamp
)

fun TranslationHistory.toEntity() = TranslationHistoryEntity(
    id = id,
    sourceText = sourceText,
    translatedText = translatedText,
    sourceLang = sourceLang,
    targetLang = targetLang,
    timestamp = timestamp
)
