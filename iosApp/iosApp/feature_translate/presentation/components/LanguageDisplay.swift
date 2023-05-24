//
//  LanguageDisplay.swift
//  iosApp
//
//  Created by Ignat Legostaev on 13.05.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LanguageDisplay: View {
    var language: UILanguage
    var body: some View {
        HStack {
            SmallLanguageIcon(language: language)
                .padding(.trailing, 5)
            Text(language.language.langName)
                .foregroundColor(Color.lightBlue)
        }
    }
}

//struct LanguageDisplay_Previews: PreviewProvider {
//    static var previews: some View {
//        LanguageDisplay()
//    }
//}
