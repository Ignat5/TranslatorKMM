package com.example.translatorkmm.android.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun TranslatorAppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (isDarkTheme) appDarkColors else appLightColors,
        typography = appTypography,
        shapes = appShapes,
        content = content
    )
}