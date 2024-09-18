import Foundation
import UIKit
import ComposeApp

class SwiftImageLoader: UIImageView {

    let urlStr: String?
    
    init(urlStr: String?) {
        self.urlStr = urlStr
        super.init(frame: CGRect(x: 0, y: 0, width: 100, height: 100))
        self.backgroundColor = .red
        load()
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func load() {
        guard let urlStr = self.urlStr, let url: URL = URL(string: urlStr) else {
            return
        }
        downloadImage(from: url) { image in
            DispatchQueue.main.async {
                self.image = image
            }
        }
    }

    func downloadImage(urlStr: String, completion: @escaping (UIImage?) -> Void) {
        guard let url: URL = URL(string: urlStr) else {
            completion(nil)
            return
        }
        downloadImage(from: url) { image in
            completion(image)
        }
       
    }
    func downloadImage(from url: URL, completion: @escaping (UIImage?) -> Void) {
        let task = URLSession.shared.dataTask(with: url) { data, response, error in
            guard let data = data, error == nil else {
                print("Error downloading image: \(String(describing: error))")
                DispatchQueue.main.async {
                    completion(nil)
                }
                return
            }
            
            let image = UIImage(data: data)
            DispatchQueue.main.async {
                completion(image)
            }
        }
        task.resume()
    }
}
