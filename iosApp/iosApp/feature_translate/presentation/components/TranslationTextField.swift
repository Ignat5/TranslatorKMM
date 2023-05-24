//
//  TranlateTextField.swift
//  iosApp
//
//  Created by Ignat Legostaev on 14.05.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared
import UniformTypeIdentifiers
import OSLog

struct TranslationTextField: View {
    @Binding var fromText: String
    var toText: String?
    var isTranslating: Bool
    var fromLanguage: UILanguage
    var toLanguage: UILanguage
    var onTranslateEvent: (TranslationScreenEvent) -> Void
    var body: some View {
        if toText == nil || isTranslating {
            IdleTextField(
                fromText: $fromText,
                isTranslating: isTranslating,
                onTranslateEvent: { event in
                    let defaultLog = Logger()
                    defaultLog.log("IdleTextField: onTranslateEvent...")
                    onTranslateEvent(event)
                }
            )
            .padding()
            .gradientSurface()
            .cornerRadius(15)
            .animation(.easeInOut, value: isTranslating)
            .shadow(radius: 4)
            .onTapGesture {
                onTranslateEvent(TranslationScreenEvent.OnEditTranslationClick())
            }
        } else {
            TranslatedTextField(
                fromText: fromText,
                toText: toText ?? "",
                fromLanguage: fromLanguage,
                toLanguage: toLanguage,
                onTranslateEvent: { event in
                    onTranslateEvent(event)
                }
            )
            .padding()
            .gradientSurface()
            .cornerRadius(15)
            .animation(.easeInOut, value: isTranslating)
            .shadow(radius: 4)
            .onTapGesture {
                onTranslateEvent(TranslationScreenEvent.OnEditTranslationClick())
            }
        }
    }
}

private extension TranslationTextField {
    struct IdleTextField: View {
        @Binding var fromText: String
        var isTranslating: Bool
        var onTranslateEvent: (TranslationScreenEvent) -> Void
        var body: some View {
            TextEditor(text: $fromText)
                .frame(
                    maxWidth: .infinity,
                    minHeight: 200,
                    alignment: .topLeading
                )
                .padding()
                .foregroundColor(Color.onSurface)
                .overlay(alignment: .bottomTrailing) {
                    ProgressButton(
                        btnText: "Translate",
                        isLoading: isTranslating,
                        onClick: {
                            let defaultLog = Logger()
                            defaultLog.log("progress btn on click...")
                            onTranslateEvent(TranslationScreenEvent.OnTranslateClick())
                        }
                    )
                    .padding(.trailing)
                    .padding(.bottom)
                }
                .onAppear {
                    UITextView.appearance().backgroundColor = .clear
                }
            
        }
    }
    
    struct TranslatedTextField: View {
        let fromText: String
        let toText: String
        let fromLanguage: UILanguage
        let toLanguage: UILanguage
        let onTranslateEvent: (TranslationScreenEvent) -> Void
        private let tts = TextToSpeech()
        var body: some View {
            VStack(alignment: .leading) {
                LanguageDisplay(language: fromLanguage)
                Text(fromText)
                    .foregroundColor(.onSurface)
                HStack {
                    Spacer()
                    Button(action: {
                        UIPasteboard.general.setValue(
                            fromText,
                            forPasteboardType: UTType.plainText.identifier
                        )
                    }) {
                        Image(uiImage: UIImage(named: "copy")!)
                            .renderingMode(.template)
                            .foregroundColor(.lightBlue)
                    }
                    Button(action: {
                        onTranslateEvent(TranslationScreenEvent.OnCloseTranslationClick())
                    }) {
                        Image(systemName: "xmark")
                            .foregroundColor(.lightBlue)
                        
                    }
                }
                Divider()
                    .padding()
                LanguageDisplay(language: toLanguage)
                Text(toText)
                    .foregroundColor(.onSurface)
                HStack {
                    Spacer()
                    Button(
                        action: {
                            UIPasteboard.general.setValue(
                                toText,
                                forPasteboardType: UTType.plainText.identifier
                            )
                        }
                    ) {
                        Image(uiImage: UIImage(named: "copy")!)
                            .renderingMode(.template)
                            .foregroundColor(.lightBlue)
                    }
                    Button(
                        action: {
                            tts.speak(
                                text: toText,
                                language: toLanguage.language.langCode
                            )
                        }
                    ) {
                        Image(systemName: "speaker.wave.2")
                            .foregroundColor(.lightBlue)
                    }
                }
            }
        }
    }
}

struct TranlateTextField_Previews: PreviewProvider {
    static var previews: some View {
        TranslationTextField(
            fromText: Binding(
                get: { "test string" },
                set: { str in }
            ),
            toText: nil,
            isTranslating: true,
            fromLanguage: UILanguage.companion.byCode(langCode: "en"),
            toLanguage: UILanguage.companion.byCode(langCode: "uk"),
            onTranslateEvent: { event in }
        )
        .preferredColorScheme(.dark)
    }
}
