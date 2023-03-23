package com.example.translatorkmm.core.domain.use_case.read_history

import com.example.translatorkmm.core.domain.util.CommonFlow
import com.example.translatorkmm.feature_history.domain.model.TranslationHistoryModel

interface ReadHistoryUseCase {
    operator fun invoke(): CommonFlow<List<TranslationHistoryModel>>
}