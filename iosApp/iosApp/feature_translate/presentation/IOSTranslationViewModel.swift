//
//  IOSTranslationViewModel.swift
//  iosApp
//
//  Created by Ignat Legostaev on 12.05.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

extension TranslationScreen {
    @MainActor class IOSTranslationViewModel: ObservableObject {
        private var readHistoryUseCase: ReadHistoryUseCase
        private var translateUseCase: TranslateUseCase
        private let viewModel: TranslationViewModel
        
        @Published var state: TranslationScreenState = TranslationScreenState(
            fromText: "", toText: nil, fromLanguage: UILanguage(language: Language.english, imageName: "english"), toLanguage: UILanguage(language: Language.german, imageName: "german"), isTranslating: false, isChoosingFromLanguage: false, isChoosingToLanguage: false, error: nil, historyList: [])
        
        init(readHistoryUseCase: ReadHistoryUseCase, translateUseCase: TranslateUseCase) {
            self.readHistoryUseCase = readHistoryUseCase
            self.translateUseCase = translateUseCase
            self.viewModel = TranslationViewModel(translateUseCase: translateUseCase, readHistoryUseCase: readHistoryUseCase, coroutineScope: nil)
        }
        
        func onEvent(event: TranslationScreenEvent) {
            self.viewModel.onEvent(event: event)
        }
        
        private var handle: IOSDisposableHandle?
        
        func startObserving() {
            handle = viewModel.screenState.subscribe(onCollect: { state in
                if let state = state {
                    self.state = state
                }
            })
        }
        
        func dispose() {
            handle?.dispose()
        }
    }
}
