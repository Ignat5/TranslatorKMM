package com.example.translatorkmm.android.feature_translation.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.translatorkmm.android.core.theme.LightBlue
import com.example.translatorkmm.core.presentation.UILanguage

@Composable
fun LanguageDropDown(
    currentLanguage: UILanguage,
    isMenuOpen: Boolean,
    onClick: () -> Unit,
    onDismiss: () -> Unit,
    onSelectLanguage: (language: UILanguage) -> Unit,
    modifier: Modifier = Modifier,
    allLanguages: List<UILanguage> = UILanguage.allLanguages
) {
    Box(modifier = modifier) {
        DropdownMenu(expanded = isMenuOpen, onDismissRequest = onDismiss) {
            allLanguages.forEach { language ->
                LanguageDropDownItem(
                    uiLanguage = language,
                    onClick = {
                        onSelectLanguage(language)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Row(
            modifier = Modifier
                .clickable {
                    onClick()
                }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = currentLanguage.drawableRes,
                contentDescription = currentLanguage.language.langName,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = currentLanguage.language.langName,
                color = LightBlue
            )
            Icon(
                imageVector = if (isMenuOpen) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                contentDescription = if (isMenuOpen) "open menu" else "close menu",
                tint = LightBlue,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}
