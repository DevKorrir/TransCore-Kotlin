package com.example.transcore.presentation.ui.history.data.repository

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
