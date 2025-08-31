package com.example.transcore.presentation.ui.translateScreen.view

import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.transcore.presentation.ui.translateScreen.componets.ActionButtonsRow
import com.example.transcore.presentation.ui.translateScreen.componets.LanguageSelectionRow
import com.example.transcore.presentation.ui.translateScreen.componets.TransCoreHeader
import com.example.transcore.presentation.ui.translateScreen.componets.TranslationCardsSection
import com.example.transcore.presentation.viewModel.TranslatorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TranslateScreen(
    modifier: Modifier = Modifier,
    viewModel: TranslatorViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    // Animation states
    val swapRotation by animateFloatAsState(
        targetValue = if (uiState.isLoading) 360f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "swap_rotation"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.surface,
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Header with App Branding
            TransCoreHeader()

            // Language Selection Row
            LanguageSelectionRow(
                uiState = uiState,
                onSourceLanguageSelected = viewModel::selectSourceLanguage,
                onTargetLanguageSelected = viewModel::selectTargetLanguage,
                onSwapLanguages = viewModel::swapLanguages,
                swapRotation = swapRotation
            )

            // Translation Cards
            TranslationCardsSection(
                sourceText = uiState.sourceText,
                translatedText = uiState.translatedText,
                isLoading = uiState.isLoading,
                onTextChanged = viewModel::onTextChanged,
                onTranslate = { viewModel.translateText() },
                onClear = viewModel::clearText,
                onCopyTranslation = {
                    clipboardManager.setText(AnnotatedString(uiState.translatedText))
                }
            )

            // Action Buttons
            ActionButtonsRow(
                hasText = uiState.sourceText.isNotBlank(),
                hasTranslation = uiState.translatedText.isNotBlank(),
                isLoading = uiState.isLoading,
                onTranslate = { viewModel.translateText() },
                onClear = viewModel::clearText,
                onCopy = {
                    clipboardManager.setText(AnnotatedString(uiState.translatedText))
                }
            )
        }

        // Error Snackbar
        uiState.error?.let { error ->
            LaunchedEffect(error) {
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            }
        }
    }
}