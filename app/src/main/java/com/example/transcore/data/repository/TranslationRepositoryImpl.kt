package com.example.transcore.data.repository

import com.example.transcore.data.api.TranslateApiService
import com.example.transcore.data.models.TranslateRequest
import com.example.transcore.data.models.TranslationResult
import com.example.transcore.data.models.Language
import com.example.transcore.domain.repos.TranslateRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TranslateRepositoryImpl @Inject constructor(
    private val apiService: TranslateApiService
) : TranslateRepository {

    override suspend fun getLanguages(): Result<List<Language>> = runCatching {
        apiService.getLanguages()
    }

    override suspend fun translateText(
        text: String,
        sourceLanguage: String,
        targetLanguage: String
    ): Result<TranslationResult> = runCatching {
        val response = apiService.translate(
            TranslateRequest(
                q = text,
                source = sourceLanguage,
                target = targetLanguage
            )
        )
        TranslationResult(response.translatedText)
    }
}