package com.example.translatorkmm.android.feature_translation.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import com.example.translatorkmm.core.presentation.UILanguage

@Composable
fun LanguageDropDownItem(
    uiLanguage: UILanguage,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    DropdownMenuItem(
        onClick = onClick,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = uiLanguage.drawableRes),
            contentDescription = uiLanguage.language.langName,
            modifier = Modifier
                .size(40.dp)
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Text(text = uiLanguage.language.langName.lowercase().capitalize(Locale.current))
    }
}