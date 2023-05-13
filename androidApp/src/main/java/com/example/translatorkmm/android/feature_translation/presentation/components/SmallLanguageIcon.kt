package com.example.translatorkmm.android.feature_translation.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.translatorkmm.core.presentation.UILanguage

@Composable
fun SmallLanguageIcon(
    language: UILanguage,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = language.drawableRes,
        contentDescription = language.language.langName,
        modifier = modifier.size(25.dp)
    )
}

@Preview
@Composable
fun SmallLanguageIconPreview() {
    UILanguage.allLanguages.firstOrNull()?.let { language ->
        SmallLanguageIcon(language = language)
    }
}