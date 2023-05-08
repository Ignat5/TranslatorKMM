package com.example.translatorkmm.android.feature_translation.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.translatorkmm.android.R
import com.example.translatorkmm.android.core.theme.LightBlue
import com.example.translatorkmm.core.presentation.UILanguage

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TranslationTextField(
    fromText: String,
    toText: String?,
    isTranslating: Boolean,
    fromLanguage: UILanguage,
    toLanguage: UILanguage,
    onTranslateClick: () -> Unit,
    onTextChange: (String) -> Unit,
    onCopyClick: (String) -> Unit,
    onCloseClick: () -> Unit,
    onSpeakerClick: () -> Unit,
    onTextFieldClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .translationGradient()
            .clickable(onClick = onTextFieldClick)
            .padding(top = 16.dp, end = 16.dp, start = 16.dp, bottom = 16.dp)
    ) {
        AnimatedContent(targetState = toText) { toText ->
            if (toText == null || isTranslating) {
                IdleTranslationTextFiled(
                    fromText = fromText,
                    isTranslating = isTranslating,
                    onTextChange = onTextChange,
                    onTranslateClick = onTranslateClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f)
                )
            } else {
                TranslatedTextField(
                    fromText = fromText,
                    toText = toText ?: "",
                    fromLanguage = fromLanguage,
                    toLanguage = toLanguage,
                    onCopyClick = onCopyClick,
                    onCloseClick = onCloseClick,
                    onSpeakerClick = onSpeakerClick,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }

}

@Composable
private fun TranslatedTextField(
    fromText: String,
    toText: String,
    fromLanguage: UILanguage,
    toLanguage: UILanguage,
    onCopyClick: (String) -> Unit,
    onCloseClick: () -> Unit,
    onSpeakerClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        /** top text field **/
        TranslationTextField(language = fromLanguage, text = fromText, actionIcons = listOf(
            ActionIcon(
                iconDrawableId = R.drawable.copy,
                contentDescription = "copy",
                onIconClick = {
                    onCopyClick(fromText)
                }
            ),
            ActionIcon(
                iconDrawableId = R.drawable.close,
                contentDescription = "close",
                onCloseClick
            )
        ))
        Spacer(modifier = Modifier.height(16.dp))
        Divider()
        Spacer(modifier = Modifier.height(16.dp))
        /** bottom text field **/
        TranslationTextField(
            language = toLanguage,
            text = toText,
            actionIcons = listOf(
                ActionIcon(
                    iconDrawableId = R.drawable.copy,
                    contentDescription = "copy",
                    onIconClick = {
                        onCopyClick(toText)
                    }
                ),
                ActionIcon(
                    iconDrawableId = R.drawable.speaker,
                    contentDescription = "close",
                    onSpeakerClick
                )
            )
        )
    }
}

data class ActionIcon(
    @DrawableRes val iconDrawableId: Int,
    val contentDescription: String,
    val onIconClick: () -> Unit
)

@Composable
private fun TranslationTextField(
    language: UILanguage,
    text: String,
    actionIcons: List<ActionIcon>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        LanguageDisplay(language = language)
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = text,
            color = MaterialTheme.colors.onSurface
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Row(
            modifier = Modifier
                .align(Alignment.End)
        ) {
            actionIcons.forEach { singleActionIcon ->
                IconButton(onClick = { singleActionIcon.onIconClick() }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = singleActionIcon.iconDrawableId),
                        contentDescription = singleActionIcon.contentDescription,
                        tint = LightBlue
                    )
                }
            }
        }
    }
}

@Composable
private fun IdleTranslationTextFiled(
    fromText: String,
    isTranslating: Boolean,
    onTextChange: (String) -> Unit,
    onTranslateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isFocused by remember {
        mutableStateOf(false)
    }

    Box(modifier = modifier) {
        BasicTextField(
            value = fromText,
            onValueChange = onTextChange,
            cursorBrush = SolidColor(MaterialTheme.colors.primary),
            modifier = Modifier
                .fillMaxSize()
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            textStyle = TextStyle(
                color = MaterialTheme.colors.onSurface
            )
        )
        /** hint **/
        if (fromText.isEmpty() && !isFocused) {
            Text(
                text = stringResource(id = R.string.enter_text),
                color = LightBlue
            )
        }
        ProgressButton(
            text = stringResource(id = R.string.translate),
            isLoading = isTranslating,
            onClick = onTranslateClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
        )
    }

}

