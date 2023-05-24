//
//  ProgressButton.swift
//  iosApp
//
//  Created by Ignat Legostaev on 14.05.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct ProgressButton: View {
    var btnText: String
    var isLoading: Bool
    var onClick: () -> Void
    var body: some View {
        Button(
            action: {
                if (!isLoading) {
                    onClick()
                }
            },
            label: {
                if (isLoading) {
                    ProgressView()
                        .animation(Animation.easeInOut, value: isLoading)
                        .padding(5)
                        .background(Color.primaryColor)
                        .cornerRadius(100)
                        .progressViewStyle(CircularProgressViewStyle(tint: .white))
                } else {
                    Text(btnText.uppercased())
                        .animation(Animation.easeInOut, value: isLoading)
                        .padding(.horizontal)
                        .padding(.vertical, 5)
                        .font(Font.body.weight(Font.Weight.bold))
                        .background(Color.primaryColor)
                        .foregroundColor(Color.onPrimary)
                        .cornerRadius(100)
                }
            }
        )
    }
}

struct ProgressButton_Previews: PreviewProvider {
    static var previews: some View {
        ProgressButton(
            btnText: "Translate", isLoading: true, onClick: {}
        )
    }
}
