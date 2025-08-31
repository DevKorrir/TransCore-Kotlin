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
import com.example.transcore.presentation.uiState.TranslatorUiState
import com.example.transcore.presentation.viewModel.TranslatorViewModel

@Composable
fun LanguageSelectionRow(
    onSourceLanguageSelected: (Language) -> Unit,
    onTargetLanguageSelected: (Language) -> Unit,
    onSwapLanguages: () -> Unit,
    viewModel: TranslatorViewModel = hiltViewModel(),
    uiState: TranslatorUiState
) {

    var showSourceSheet by remember { mutableStateOf(false) }
    var showTargetSheet by remember { mutableStateOf(false) }

    val swapRotation = if (uiState.sourceLanguage == uiState.targetLanguage) 180f else 0f
    var showSheet by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()

    // Bottom Sheets
    if (showSourceSheet) {
        LanguageBottomSheet(
            languages = uiState.availableLanguages,
            selectedLanguage = uiState.sourceLanguage,
            onLanguageSelected = {
                onSourceLanguageSelected(it)
                showSourceSheet = false
            },
            onDismiss = { showSourceSheet = false }
        )
    }

    if (showTargetSheet) {
        LanguageBottomSheet(
            languages = uiState.availableLanguages,
            selectedLanguage = uiState.targetLanguage,
            onLanguageSelected = {
                onTargetLanguageSelected(it)
                showTargetSheet = false
            },
            onDismiss = { showTargetSheet = false }
        )
    }

    //row
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
                onClick = { showSourceSheet = true },
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
                onClick = { showTargetSheet = true },
                modifier = Modifier.weight(1f)
            )
        }
    }
}