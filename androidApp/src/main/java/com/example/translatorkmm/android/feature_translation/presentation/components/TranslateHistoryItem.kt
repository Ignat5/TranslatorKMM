package com.example.translatorkmm.android.feature_translation.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.translatorkmm.android.core.theme.LightBlue
import com.example.translatorkmm.core.presentation.UILanguage
import com.example.translatorkmm.feature_history.presentation.UITranslationHistory

@Composable
fun TranslationHistoryItem(
    item: UITranslationHistory,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .translationGradient()
            .clickable {
                onClick()
            }
            .padding(16.dp)
    ) {
        TranslationHistoryEntry(
            entryLanguage = item.translationFromLanguage,
            entryText = item.translationFromText,
            isHighlighted = false
        )
        Spacer(modifier = Modifier.height(24.dp))
        TranslationHistoryEntry(
            entryLanguage = item.translationToLanguage,
            entryText = item.translationToText,
            isHighlighted = true
        )
    }
}

@Composable
private fun TranslationHistoryEntry(
    entryLanguage: UILanguage,
    entryText: String,
    isHighlighted: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SmallLanguageIcon(language = entryLanguage)
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = entryText,
            color = if (isHighlighted) MaterialTheme.colors.onSurface else LightBlue,
            style = if (isHighlighted) MaterialTheme.typography.body1 else MaterialTheme.typography.body2,
            fontWeight = if (isHighlighted) FontWeight.Medium else FontWeight.Normal
        )
    }
}