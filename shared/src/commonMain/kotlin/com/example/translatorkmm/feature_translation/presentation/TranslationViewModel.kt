package com.example.translatorkmm.feature_translation.presentation

import com.example.translatorkmm.core.domain.result.ResultModel
import com.example.translatorkmm.core.domain.use_case.read_history.ReadHistoryUseCase
import com.example.translatorkmm.core.domain.use_case.translate.TranslateUseCase
import com.example.translatorkmm.core.domain.util.CoreMapper.toUITranslationHistory
import com.example.translatorkmm.core.domain.util.toCommonStateFlow
import com.example.translatorkmm.feature_translation.domain.TranslationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TranslationViewModel(
    private val translateUseCase: TranslateUseCase,
    private val readHistoryUseCase: ReadHistoryUseCase,
    private val coroutineScope: CoroutineScope?
) {

    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val defaultTranslationScreenState = TranslationScreenState()

    private val _screenState: MutableStateFlow<TranslationScreenState> =
        MutableStateFlow(defaultTranslationScreenState)
    val screenState = combine(
        _screenState,
        readHistoryUseCase()
    ) { currentScreenState, currentHistory ->
        if (currentScreenState.historyList != currentHistory)
            currentScreenState.copy(
                historyList = currentHistory.map { it.toUITranslationHistory() }
            ) else currentScreenState
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        defaultTranslationScreenState
    ).toCommonStateFlow()

    private var currentTranslationJob: Job? = null

    fun onEvent(event: TranslationScreenEvent) {
        when (event) {
            is TranslationScreenEvent.OnTranslateClick -> onTranslateClick(screenState.value)
            is TranslationScreenEvent.OnChooseFromLanguageClick -> onChooseFromLanguageClick()
            is TranslationScreenEvent.OnChooseToLanguageClick -> onChooseToLanguageClick()
            is TranslationScreenEvent.OnCloseTranslationClick -> onCloseTranslationClick()
            is TranslationScreenEvent.OnTranslateFromTextChange -> onTranslateFromTextChange(event)
            is TranslationScreenEvent.OnErrorSeen -> onErrorSeen()
            is TranslationScreenEvent.OnExitChooseLanguageClick -> onExitChooseLanguageClick()
            is TranslationScreenEvent.OnFromLanguageChosen -> onFromLanguageChosen(event)
            is TranslationScreenEvent.OnToLanguageChosen -> onToLanguageChosen(event)
            is TranslationScreenEvent.OnRecordFromTextClick -> onRecordFromTextClick()
            is TranslationScreenEvent.OnSwapLanguagesClick -> onSwapLanguagesClick()
            is TranslationScreenEvent.OnRecordingResultSubmit -> onRecordingResultSubmit(event)
            is TranslationScreenEvent.OnTranslationHistoryItemClick -> onTranslationHistoryItemClick(
                event
            )
            is TranslationScreenEvent.OnEditTranslationClick -> onEditTranslationClick()
        }
    }

    private fun onEditTranslationClick() {
        if (screenState.value.toText != null) {
            _screenState.update {
                it.copy(
                    toText = null,
                    isTranslating = false
                )
            }
        }
    }

    private fun onRecordFromTextClick() {
        // TODO
    }

    private fun onSwapLanguagesClick() {
        val updatedState = _screenState.updateAndGet {
            it.copy(
                isTranslating = false,
                fromLanguage = it.toLanguage,
                toLanguage = it.fromLanguage,
                fromText = it.toText ?: it.fromText,
                toText = if (it.toText != null) it.fromText else it.toText
            )
        }
        onTranslateClick(updatedState)
    }

    private fun onRecordingResultSubmit(event: TranslationScreenEvent.OnRecordingResultSubmit) {
        val resultFromText: String? = event.result
        _screenState.update {
            it.copy(
                fromText = resultFromText ?: it.fromText,
                toText = if (resultFromText != null) null else it.toText,
                isTranslating = if (resultFromText != null) false else it.isTranslating
            )
        }
    }

    private fun onTranslationHistoryItemClick(event: TranslationScreenEvent.OnTranslationHistoryItemClick) {
        currentTranslationJob?.cancel()
        val chosenHistoryItem = event.translationHistory
        _screenState.update {
            it.copy(
                isTranslating = false,
                fromText = chosenHistoryItem.translationFromText,
                toText = chosenHistoryItem.translationToText,
                fromLanguage = chosenHistoryItem.translationFromLanguage,
                toLanguage = chosenHistoryItem.translationToLanguage
            )
        }
    }

    private fun onExitChooseLanguageClick() {
        _screenState.update {
            it.copy(
                isChoosingToLanguage = false,
                isChoosingFromLanguage = false
            )
        }
    }

    private fun onErrorSeen() {
        _screenState.update {
            it.copy(
                error = null
            )
        }
    }

    private fun onCloseTranslationClick() {
        _screenState.update {
            it.copy(
                isTranslating = false,
                fromText = "",
                toText = null
            )
        }
    }

    private fun onFromLanguageChosen(event: TranslationScreenEvent.OnFromLanguageChosen) {
        val updatedState = _screenState.updateAndGet {
            it.copy(
                fromLanguage = event.language,
                isChoosingFromLanguage = false
            )
        }
        onTranslateClick(updatedState)
    }

    private fun onToLanguageChosen(event: TranslationScreenEvent.OnToLanguageChosen) {
        val updatedState = _screenState.updateAndGet {
            it.copy(
                toLanguage = event.language,
                isChoosingToLanguage = false
            )
        }
        onTranslateClick(updatedState)
    }

    private fun onChooseFromLanguageClick() {
        _screenState.update {
            it.copy(
                isChoosingFromLanguage = !it.isChoosingFromLanguage
            )
        }
    }

    private fun onChooseToLanguageClick() {
        _screenState.update {
            it.copy(
                isChoosingToLanguage = !it.isChoosingToLanguage
            )
        }
    }

    private fun onTranslateFromTextChange(event: TranslationScreenEvent.OnTranslateFromTextChange) {
        _screenState.update {
            it.copy(
                fromText = event.text,
                toText = null,
                isTranslating = false
            )
        }
    }

    private fun onTranslateClick(state: TranslationScreenState) {
        if (state.isTranslating || state.fromText.isBlank()) return
        currentTranslationJob?.cancel()
        currentTranslationJob = viewModelScope.launch {
            _screenState.update {
                it.copy(
                    isTranslating = true
                )
            }
            val translationResult = translateUseCase(
                fromLanguage = state.fromLanguage.language,
                fromText = state.fromText,
                toLanguage = state.toLanguage.language
            )
            when (translationResult) {
                is ResultModel.Success -> {
                    _screenState.update {
                        it.copy(
                            isTranslating = false,
                            toText = translationResult.data.translationToText
                        )
                    }
                }
                is ResultModel.Error -> {
                    _screenState.update {
                        it.copy(
                            isTranslating = false,
                            error = (translationResult.throwable as? TranslationException)?.error
                        )
                    }
                }
            }
        }
    }

}