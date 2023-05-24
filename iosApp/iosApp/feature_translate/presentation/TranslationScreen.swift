//
//  TranslationScreen.swift
//  iosApp
//
//  Created by Ignat Legostaev on 12.05.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared
import OSLog

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
        ZStack(alignment: .top) {
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
                
                TranslationTextField(
                    fromText: Binding(
                        get: {
                            viewModel.state.fromText
                        },
                        set: { value in
                            viewModel.onEvent(event: TranslationScreenEvent.OnTranslateFromTextChange(text: value))
                        }
                    ),
                    toText: viewModel.state.toText,
                    isTranslating: viewModel.state.isTranslating,
                    fromLanguage: viewModel.state.fromLanguage,
                    toLanguage: viewModel.state.toLanguage,
                    onTranslateEvent: { event in
                        let defaultLog = Logger()
                        defaultLog.log("TranslationTextField: onTranslateEvent...fromText: \(viewModel.state.fromText), event: \(event)")
                        viewModel.onEvent(event: event)
                    })
                .listRowSeparator(Visibility.hidden)
                .listRowBackground(Color.background)
                if (!viewModel.state.historyList.isEmpty) {
                    Text("History")
                        .font(.title)
                        .foregroundColor(Color.onSurface)
                        .bold()
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .listRowSeparator(.hidden)
                        .listRowBackground(Color.background)
                    
                    ForEach(viewModel.state.historyList, id: \.self.translationId) { historyItem in
                        TranslationHistoryItem(
                            historyItem: historyItem,
                            onClick: {
                                viewModel.onEvent(event: TranslationScreenEvent.OnTranslationHistoryItemClick(
                                    translationHistory: historyItem
                                ))
                            }
                        )
                        .listRowSeparator(Visibility.hidden)
                        .listRowBackground(Color.background)
                    }
                }
                
            }
            .listStyle(.plain)
            .buttonStyle(.plain)
            .background(Color.background)
            
            
            // voice-to-text button
            VStack {
                Spacer()
                NavigationLink(destination: Text("voice-to-text screen")) {
                    ZStack {
                        Circle()
                            .foregroundColor(Color.primaryColor)
                            .padding()
                        if let micImage = UIImage(named: "mic") {
                            Image(uiImage: micImage)
                                .foregroundColor(Color.onPrimary)
                        }
                    }
                    .frame(maxWidth: 100, maxHeight: 100)
                }
            }
        }
        .onAppear {
            viewModel.startObserving()
        }
        .onDisappear {
            viewModel.dispose()
        }
    }
}
