import SwiftUI
import shared

struct ContentView: View {
    
    private let appModule = AppModule()

	var body: some View {
        ZStack {
            Color.background
                .ignoresSafeArea()
            TranslationScreen(
                translateUseCase: appModule.translateUseCase,
                readHistoryUseCase: appModule.readHistoryUseCase
            )
        }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
