//
//  TextToSpeach.swift
//  iosApp
//
//  Created by Ignat Legostaev on 16.05.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import AVFoundation

struct TextToSpeech {
    private let synthesizer = AVSpeechSynthesizer()
    func speak(text: String, language: String) {
        let utterance = AVSpeechUtterance(string: text)
        utterance.voice = AVSpeechSynthesisVoice(language: language)
        utterance.volume = 1
        synthesizer.speak(utterance)
    }
}
