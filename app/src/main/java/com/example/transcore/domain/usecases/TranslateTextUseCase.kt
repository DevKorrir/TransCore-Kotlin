package com.example.transcore.domain.usecases

import com.example.transcore.data.models.Language
import com.example.transcore.data.models.TranslationResult
import com.example.transcore.domain.repos.TranslateRepository
import javax.inject.Inject

class TranslateTextUseCase @Inject constructor(
    private val repository: TranslateRepository
) {
    suspend operator fun invoke(
        text: String,
        sourceLanguage: String,
        targetLanguage: String
    ): Result<TranslationResult> {
        return repository.translateText(text, sourceLanguage, targetLanguage)
    }
}

