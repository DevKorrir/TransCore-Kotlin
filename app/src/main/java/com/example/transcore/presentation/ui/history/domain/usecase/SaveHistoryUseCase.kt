package com.example.transcore.presentation.ui.history.domain.usecase

import com.example.transcore.presentation.ui.history.domain.model.TranslationHistory
import com.example.transcore.presentation.ui.history.domain.repo.TranslationRepository
import javax.inject.Inject

class SaveHistoryUseCase @Inject constructor(
    private val repo: TranslationRepository
) {
    suspend operator fun invoke(item: TranslationHistory) = repo.saveHistory(item)
}
