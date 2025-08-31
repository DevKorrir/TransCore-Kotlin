package com.example.transcore.data.repository

import android.util.Log
import com.example.transcore.data.api.TranslateApiService
import com.example.transcore.data.models.Language
import com.example.transcore.data.models.TranslateRequest
import com.example.transcore.data.models.TranslationResult
import com.example.transcore.domain.repos.TranslateRepository
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TranslateRepositoryImpl @Inject constructor(
    private val apiService: TranslateApiService
) : TranslateRepository {

    override suspend fun getLanguages(): Result<List<Language>> = runCatching {
        Timber.tag("TranslateRepo").d("Loading offline languages")
        Language.SUPPORTED_LANGUAGES
    }

    override suspend fun translateText(
        text: String,
        sourceLanguage: String,
        targetLanguage: String
    ): Result<TranslationResult> = runCatching {
        Timber.tag("TranslateRepo").d("Translating: $text from $sourceLanguage to $targetLanguage")
        val translateRequestBody = TranslateRequest(
            q = text,
            source = sourceLanguage,
            target = targetLanguage,
        )
        val response = apiService.translate(
            request = translateRequestBody
        )
        Timber.tag("TranslateRepo").d("API response: $response")

        // Convert API response to domain model
        TranslationResult(
            translatedText = response.data.translations.firstOrNull()?.translatedText.orEmpty()
        )

    }.onFailure { e ->
        Timber.tag("TranslateRepo").e(e, "Error translating text")

    }
}