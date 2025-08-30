package com.example.transcore.domain.usecases

import com.example.transcore.data.models.Language
import com.example.transcore.domain.repos.TranslateRepository
import javax.inject.Inject

class GetLanguagesUseCase @Inject constructor(
    private val repository: TranslateRepository
) {
    suspend operator fun invoke(): Result<List<Language>> {
        return repository.getLanguages()
    }
}