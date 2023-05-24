//
//  TranslationHistoryItem.swift
//  iosApp
//
//  Created by Ignat Legostaev on 16.05.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TranslationHistoryItem: View {
    let historyItem: UITranslationHistory
    let onClick: () -> Void
    var body: some View {
        Button(action: {
            onClick()
        }) {
            VStack(
                alignment: HorizontalAlignment.leading
            ) {
                TranslationHistoryPart(
                    language: historyItem.translationFromLanguage,
                    translationText: historyItem.translationFromText,
                    hasFocus: false
                )
                TranslationHistoryPart(
                    language: historyItem.translationToLanguage,
                    translationText: historyItem.translationToText,
                    hasFocus: true
                )
            }
            .frame(maxWidth: .infinity, alignment: .leading)
            .gradientSurface()
            .cornerRadius(15)
            .shadow(radius: 15)
        }
    }
}

struct TranslationHistoryPart: View {
    let language: UILanguage
    let translationText: String
    let hasFocus: Bool
    var body: some View {
        let uiImage = UIImage(named: language.imageName.lowercased())
        let opacity = hasFocus ? 1.0 : 0.5
        if let langUIImage = uiImage {
            HStack {
                Image(uiImage: langUIImage)
                    .resizable()
                    .frame(width: 35, height: 35)
                    .padding()
                Text(translationText)
                    .foregroundColor(Color.onSurface)
                    .opacity(opacity)
                    .padding(.trailing)
            }
            
        }
    }
}

struct TranslationHistoryItem_Previews: PreviewProvider {
    static var previews: some View {
        TranslationHistoryItem(
            historyItem: UITranslationHistory(
                translationId: "",
                translationFromLanguage: UILanguage.companion.byCode(langCode: "en"),
                translationFromText: "hello",
                translationToLanguage: UILanguage.companion.byCode(langCode: "de"),
                translationToText: "hello on german",
                translationCreatedAt: 0),
            onClick: {
                
            }
        )
        .preferredColorScheme(.dark)
    }
}
