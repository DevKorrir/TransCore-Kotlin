package com.example.transcore.presentation.ui.translateScreen.componets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.transcore.data.models.Language
import com.example.transcore.presentation.viewModel.TranslatorViewModel

@Composable
fun LanguageSelectionRow(
    sourceLanguage: Language,
    targetLanguage: Language,
    onSourceLanguageSelected: (Language) -> Unit,
    onTargetLanguageSelected: (Language) -> Unit,
    onSwapLanguages: () -> Unit,
    swapRotation: Float,
    availableLanguages: List<Language>,
    viewModel: TranslatorViewModel = hiltViewModel()
) {
    var showSheet by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()

    if (showSheet) {
        LanguageBottomSheet(
            languages = uiState.availableLanguages,
            selectedLanguage = uiState.sourceLanguage, // or target
            onLanguageSelected = viewModel::selectSourceLanguage,
            onDismiss = { showSheet = false }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Source Language
            LanguageChip(
                language = uiState.sourceLanguage,
                onClick = { showSheet = true },
                modifier = Modifier.weight(1f)
            )

            // Swap Button
            FloatingActionButton(
                onClick = onSwapLanguages,
                modifier = Modifier
                    .size(48.dp)
                    .rotate(swapRotation),
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                Icon(
                    imageVector = Icons.Default.SwapHoriz,
                    contentDescription = "Swap Languages",
                    modifier = Modifier.size(24.dp)
                )
            }

            // Target Language
            LanguageChip(
                language = uiState.targetLanguage,
                onClick = { showSheet = true },
                modifier = Modifier.weight(1f)
            )
        }
    }
}