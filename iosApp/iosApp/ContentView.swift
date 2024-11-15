import UIKit
import SwiftUI
import ComposeApp

struct PreviewImageKt: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct WellnessViewKt: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.WellnessViewController()
    }
    
    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct WellnessView: View {
    var body: some View {
        WellnessViewKt()
            .ignoresSafeArea(.keyboard)
    }
}

struct PreviewImageView: View {
    var body: some View {
        PreviewImageKt()
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}



