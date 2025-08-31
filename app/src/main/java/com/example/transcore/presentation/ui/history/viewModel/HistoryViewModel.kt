package com.example.transcore.presentation.ui.history.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transcore.presentation.ui.history.domain.model.TranslationHistory
import com.example.transcore.presentation.ui.history.domain.repo.TranslationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repo: TranslationRepository
) : ViewModel() {

    val history: StateFlow<List<TranslationHistory>> =
        repo.getHistory().stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            emptyList()
        )

    fun save(item: TranslationHistory) {
        viewModelScope.launch {
            repo.saveHistory(item)
        }
    }

//    fun saveTranslation(original: String, translated: String) {
//        viewModelScope.launch {
//            repo.saveHistory(
//                TranslationHistory(
//                    originalText = original,
//                    translatedText = translated,
//                    timestamp = System.currentTimeMillis()
//                )
//            )
//        }
//    }


    fun delete(item: TranslationHistory) {
        viewModelScope.launch { repo.deleteHistory(item) }
    }
}
