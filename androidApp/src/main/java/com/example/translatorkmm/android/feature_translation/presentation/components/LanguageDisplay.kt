package com.example.translatorkmm.android.feature_translation.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.translatorkmm.android.core.theme.LightBlue
import com.example.translatorkmm.core.presentation.UILanguage

@Composable
fun LanguageDisplay(
    language: UILanguage,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SmallLanguageIcon(language = language)
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            text = language.language.langName,
            color = LightBlue
        )
    }
}