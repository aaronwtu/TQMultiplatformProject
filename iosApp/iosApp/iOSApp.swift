import SwiftUI
import ComposeApp

class ClassInt<T> {
    var value: T
    init(value: T) {
        self.value = value
    }
}

func configuration() {
    
    let stack = Stack<ClassInt<Int>>()
    for i in 0..<10 {
        stack.push(value: ClassInt(value: i))
    }
    for _ in 0..<11 {
        if let value = stack.pop() {
            print("===> result \(value.value)")
        }
    }
}

@main
struct iOSApp: App {
    init() {
        configuration()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
