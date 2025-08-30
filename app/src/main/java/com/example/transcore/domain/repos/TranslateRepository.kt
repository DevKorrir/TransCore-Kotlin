package com.example.transcore.domain.repos

import com.example.transcore.data.models.Language
import com.example.transcore.data.models.TranslationResult

interface TranslateRepository {
    suspend fun getLanguages(): Result<List<Language>>
    suspend fun translateText(
        text: String,
        sourceLanguage: String,
        targetLanguage: String
    ): Result<TranslationResult>
}