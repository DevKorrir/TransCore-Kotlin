package com.example.transcore.presentation.ui.translateScreen.componets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TranslationCardsSection(
    sourceText: String,
    translatedText: String,
    isLoading: Boolean,
    onTextChanged: (String) -> Unit,
    onTranslate: () -> Unit,
    onClear: () -> Unit,
    onCopyTranslation: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Source Text Input Card
        TranslationInputCard(
            text = sourceText,
            onTextChanged = onTextChanged,
            placeholder = "Enter text to translate...",
            onClear = onClear,
            isInput = true
        )

        // Translation Arrow
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        // Translation Result Card
        TranslationResultCard(
            translatedText = translatedText,
            isLoading = isLoading,
            onCopy = onCopyTranslation
        )
    }
}