


import Foundation

final class SessionCookieManager {

    static let shared = SessionCookieManager()

    private let cookieStorage = HTTPCookieStorage.shared

    private init() {}


    func saveCookies(from response: HTTPURLResponse) {

        guard let url = response.url,
              let headers = response.allHeaderFields as? [String: String],
              let cookies = HTTPCookie.cookies(
                withResponseHeaderFields: headers,
                for: url
              ) as [HTTPCookie]?
        else {
            return
        }

        for cookie in cookies {

            if let existingCookies = cookieStorage.cookies {
                for existing in existingCookies {
                    if existing.name == cookie.name &&
                       existing.domain == cookie.domain &&
                       existing.path == cookie.path {

                        cookieStorage.deleteCookie(existing)
                    }
                }
            }

            if let expiration = cookie.expiresDate,
               expiration <= Date() {
                continue
            }

            cookieStorage.setCookie(cookie)
        }
    }


    func clear() {

        guard let cookies = cookieStorage.cookies else {
            return
        }

        for cookie in cookies {
            cookieStorage.deleteCookie(cookie)
        }
    }
}
