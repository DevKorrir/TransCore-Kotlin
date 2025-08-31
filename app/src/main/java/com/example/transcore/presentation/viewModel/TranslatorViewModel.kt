package com.example.transcore.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transcore.data.models.Language
import com.example.transcore.domain.usecases.GetLanguagesUseCase
import com.example.transcore.domain.usecases.TranslateTextUseCase
import com.example.transcore.presentation.ui.history.domain.model.TranslationHistory
import com.example.transcore.presentation.ui.history.viewModel.HistoryViewModel
import com.example.transcore.presentation.uiState.TranslatorUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TranslatorViewModel @Inject constructor(
    private val translateTextUseCase: TranslateTextUseCase,
    private val getLanguagesUseCase: GetLanguagesUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(TranslatorUiState())
    val uiState: StateFlow<TranslatorUiState> = _uiState.asStateFlow()
    
    // For real-time translation with debouncing
    private val textChangeFlow = MutableSharedFlow<String>()

    private val _translationResult = MutableStateFlow<String?>(null)
    val translationResult: StateFlow<String?> = _translationResult
    
    init {
        loadLanguages()
        setupRealTimeTranslation()
    }
    
    private fun setupRealTimeTranslation() {
        textChangeFlow
            .debounce(800) // Wait 800ms after user stops typing
            .distinctUntilChanged()
            .onEach { text ->
                if (text.isNotBlank() && text.length > 2) {
                    translateText(text, realTime = true)
                }
            }
            .launchIn(viewModelScope)
    }
    
    fun onTextChanged(text: String) {
        _uiState.value = _uiState.value.copy(sourceText = text)
        
        if (text.isBlank()) {
            _uiState.value = _uiState.value.copy(translatedText = "")
        } else {
            viewModelScope.launch {
                textChangeFlow.emit(text)
            }
        }
    }
    
    fun translateText(text: String = _uiState.value.sourceText, realTime: Boolean = false) {
        if (text.isBlank()) return
        
        viewModelScope.launch {
            Timber.tag("TranslatorViewModel").d("Starting Translating text: $text")
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )
            
            translateTextUseCase(
                text = text,
                sourceLanguage = _uiState.value.sourceLanguage.code,
                targetLanguage = _uiState.value.targetLanguage.code
            ).fold(
                onSuccess = { result ->
                    Timber.tag("TranslatorViewModel").d("Translation success: $result")

                    _uiState.value = _uiState.value.copy(
                        translatedText = result.translatedText,
                        isLoading = false,
                        lastTranslation = result
                    )
                },
                onFailure = { exception ->
                    Timber.e(exception, "Translation failed")
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Translation failed"
                    )
                }
            )
        }
    }



    fun translateTextAndSave(historyViewModel: HistoryViewModel) {
        viewModelScope.launch {
            translateText() // call your existing translation logic
            if (uiState.value.translatedText.isNotBlank()) {
                historyViewModel.save(
                    TranslationHistory(
                        id = 0,
                        sourceText = uiState.value.sourceText,
                        translatedText = uiState.value.translatedText,
                        sourceLang = uiState.value.sourceLanguage.name,
                        targetLang = uiState.value.targetLanguage.name,
                        timestamp = System.currentTimeMillis()
                    )
                )
            }
        }
    }

    fun swapLanguages() {
        val currentState = _uiState.value
        if (currentState.sourceLanguage.code != "auto") {
            _uiState.value = currentState.copy(
                sourceLanguage = currentState.targetLanguage,
                targetLanguage = currentState.sourceLanguage,
                sourceText = currentState.translatedText,
                translatedText = currentState.sourceText
            )
        }
    }
    
    fun selectSourceLanguage(language: Language) {
        _uiState.value = _uiState.value.copy(sourceLanguage = language)
        if (_uiState.value.sourceText.isNotBlank()) {
            translateText()
        }
    }
    
    fun selectTargetLanguage(language: Language) {
        _uiState.value = _uiState.value.copy(targetLanguage = language)
        if (_uiState.value.sourceText.isNotBlank()) {
            translateText()
        }
    }
    
    fun clearText() {
        _uiState.value = _uiState.value.copy(
            sourceText = "",
            translatedText = "",
            error = null
        )
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
    
    private fun loadLanguages() {
        viewModelScope.launch {
            getLanguagesUseCase().fold(
                onSuccess = { languages ->
                    _uiState.value = _uiState.value.copy(
                        availableLanguages = languages
                    )
                },
                onFailure = { exception ->
                    Timber.e(exception, "Failed to load languages")
                    // Fallback to static list
                    _uiState.value = _uiState.value.copy(
                        availableLanguages = Language.SUPPORTED_LANGUAGES
                    )
                }
            )
        }
    }
}