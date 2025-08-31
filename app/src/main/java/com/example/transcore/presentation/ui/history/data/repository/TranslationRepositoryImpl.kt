package com.example.transcore.presentation.ui.history.data.repository

import com.example.transcore.presentation.ui.history.data.dao.TranslationHistoryDao
import com.example.transcore.presentation.ui.history.domain.mapper.toDomain
import com.example.transcore.presentation.ui.history.domain.mapper.toEntity
import com.example.transcore.presentation.ui.history.domain.model.TranslationHistory
import com.example.transcore.presentation.ui.history.domain.repo.TranslationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TranslationRepositoryImpl @Inject constructor(
    private val historyDao: TranslationHistoryDao
) : TranslationRepository {
    override suspend fun saveHistory(item: TranslationHistory) {
        historyDao.insert(item.toEntity())
    }

    override fun getHistory(): Flow<List<TranslationHistory>> =
        historyDao.getAllHistory().map { list -> list.map { it.toDomain() } }

    override suspend fun deleteHistory(item: TranslationHistory) {
        historyDao.delete(item.toEntity())
    }
}
