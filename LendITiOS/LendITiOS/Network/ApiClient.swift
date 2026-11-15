
import Foundation

final class APIClient {

    static let shared = APIClient()

    private let baseURL = "http://localhost:8081"

    private init() {}

    

    lazy var session: URLSession = {

        let configuration = URLSessionConfiguration.default

        
        configuration.httpCookieStorage = HTTPCookieStorage.shared
        configuration.httpCookieAcceptPolicy = .always
        configuration.httpShouldSetCookies = true

        return URLSession(configuration: configuration)
    }()

  

    func makeURL(for path: String) -> URL? {
        URL(string: "\(baseURL)/\(path)")
    }

   

    func clearSession() {

        guard let serverURL = URL(string: baseURL),
              let cookies = HTTPCookieStorage.shared.cookies(for: serverURL)
        else {
            return
        }

        for cookie in cookies {
            HTTPCookieStorage.shared.deleteCookie(cookie)
        }
    }
}
