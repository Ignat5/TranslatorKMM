package com.example.translatorkmm.android.feature_translation.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.translatorkmm.core.domain.use_case.read_history.ReadHistoryUseCase
import com.example.translatorkmm.core.domain.use_case.translate.TranslateUseCase
import com.example.translatorkmm.feature_translation.presentation.TranslationScreenEvent
import com.example.translatorkmm.feature_translation.presentation.TranslationViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidTranslationViewModel @Inject constructor(
    private val translateUseCase: TranslateUseCase,
    private val readHistoryUseCase: ReadHistoryUseCase
) : ViewModel() {
    private val viewModel by lazy {
        TranslationViewModel(
            translateUseCase,
            readHistoryUseCase,
            viewModelScope
        )
    }
    val state = viewModel.screenState

    fun onEvent(event: TranslationScreenEvent) {
        viewModel.onEvent(event)
    }
}