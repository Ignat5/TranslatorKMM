//
//  LanguageDropDown.swift
//  iosApp
//
//  Created by Ignat Legostaev on 11.05.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LanguageDropDown: View {
    var language: UILanguage
    var isMenuOpen: Bool
    var selectLangCallback: (UILanguage) -> Void
    var body: some View {
        Menu {
            VStack {
                ForEach(UILanguage.Companion().allLanguages, id: \.self.language.langCode) { language in
                    LanguageDropDownItem(
                        language: language,
                        onClick: {
                            selectLangCallback(language)
                        }
                    )
                }
            }
        } label: {
            HStack {
                SmallLanguageIcon(language: language)
                Text(language.language.langName)
                    .foregroundColor(Color.lightBlue)
                    .lineLimit(1)
                    .truncationMode(Text.TruncationMode.tail)
                Image(systemName: isMenuOpen ? "chevron.up" : "chevron.down")
                    .foregroundColor(.lightBlue)
            }
        }
    }
}

struct LanguageDropDown_Previews: PreviewProvider {
    static var previews: some View {
        LanguageDropDown(
            language: UILanguage.Companion().byCode(langCode: "en"),
            isMenuOpen: false,
            selectLangCallback: { language in
                
            }
        )
    }
}
