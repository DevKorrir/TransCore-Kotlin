package com.example.transcore.presentation.ui.history.domain.repo

import com.example.transcore.presentation.ui.history.domain.model.TranslationHistory
import kotlinx.coroutines.flow.Flow

interface TranslationRepository {
    suspend fun saveHistory(item: TranslationHistory)
    fun getHistory(): Flow<List<TranslationHistory>>
    suspend fun deleteHistory(item: TranslationHistory)
}
