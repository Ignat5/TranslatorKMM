//
//  TranslationScreen.swift
//  iosApp
//
//  Created by Ignat Legostaev on 12.05.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TranslationScreen: View {
    private var translateUseCase: TranslateUseCase
    private var readHistoryUseCase: ReadHistoryUseCase
    @ObservedObject private var viewModel: IOSTranslationViewModel
    
    init(translateUseCase: TranslateUseCase, readHistoryUseCase: ReadHistoryUseCase) {
        self.translateUseCase = translateUseCase
        self.readHistoryUseCase = readHistoryUseCase
        self.viewModel = IOSTranslationViewModel(readHistoryUseCase: readHistoryUseCase, translateUseCase: translateUseCase)
    }
    var body: some View {
        ZStack {
            List {
                HStack(alignment: VerticalAlignment.center) {
                    LanguageDropDown(
                        language: self.viewModel.state.fromLanguage,
                        isMenuOpen: self.viewModel.state.isChoosingFromLanguage,
                        selectLangCallback: { language in
                            self.viewModel.onEvent(event: TranslationScreenEvent.OnFromLanguageChosen(language: language))
                    })
                    Spacer()
                    SwapLanguageButton(onClick: {
                        viewModel.onEvent(event: TranslationScreenEvent.OnSwapLanguagesClick())
                    })
                    Spacer()
                    LanguageDropDown(
                        language: viewModel.state.toLanguage,
                        isMenuOpen: viewModel.state.isChoosingToLanguage,
                        selectLangCallback: { language in
                            viewModel.onEvent(event: TranslationScreenEvent.OnToLanguageChosen(language: language))
                        }
                    )
                }
                .listRowSeparator(Visibility.hidden)
                .listRowBackground(Color.background)
            }
            .listStyle(.plain)
            .buttonStyle(.plain)
        }
        .onAppear {
            viewModel.startObserving()
        }
        .onDisappear {
            viewModel.dispose()
        }
    }
}
