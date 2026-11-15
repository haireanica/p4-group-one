
import Foundation
import Security

final class CredentialsManager {

    static let shared = CredentialsManager()

    private let service = "p4groupone.lenditios.credentials"
    private let account = "remembered-login"

    private init() {}


    private struct StoredCredentials: Codable {
        let username: String
        let password: String
        let rememberMe: Bool
    }


    func saveCredentials(
        username: String,
        password: String
    ) throws {

        let credentials = StoredCredentials(
            username: username,
            password: password,
            rememberMe: true
        )

        let data = try JSONEncoder().encode(credentials)

        let query: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrService as String: service,
            kSecAttrAccount as String: account
        ]

        let attributes: [String: Any] = [
            kSecValueData as String: data,
            kSecAttrAccessible as String:
                kSecAttrAccessibleAfterFirstUnlockThisDeviceOnly
        ]

        let updateStatus = SecItemUpdate(
            query as CFDictionary,
            attributes as CFDictionary
        )

        if updateStatus == errSecItemNotFound {

            var newItem = query
            attributes.forEach { newItem[$0.key] = $0.value }

            let addStatus = SecItemAdd(
                newItem as CFDictionary,
                nil
            )

            guard addStatus == errSecSuccess else {
                throw KeychainError.operationFailed(addStatus)
            }

        } else if updateStatus != errSecSuccess {
            throw KeychainError.operationFailed(updateStatus)
        }
    }


    private func getStoredCredentials() throws -> StoredCredentials? {

        let query: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrService as String: service,
            kSecAttrAccount as String: account,
            kSecReturnData as String: true,
            kSecMatchLimit as String: kSecMatchLimitOne
        ]

        var result: CFTypeRef?

        let status = SecItemCopyMatching(
            query as CFDictionary,
            &result
        )

        if status == errSecItemNotFound {
            return nil
        }

        guard status == errSecSuccess,
              let data = result as? Data else {
            throw KeychainError.operationFailed(status)
        }

        return try JSONDecoder().decode(
            StoredCredentials.self,
            from: data
        )
    }

    func getUsername() -> String {
        do {
            return try getStoredCredentials()?.username ?? ""
        } catch {
            print("Could not retrieve username: \(error)")
            return ""
        }
    }

    func getPassword() -> String {
        do {
            return try getStoredCredentials()?.password ?? ""
        } catch {
            print("Could not retrieve password: \(error)")
            return ""
        }
    }

    func isRememberMeEnabled() -> Bool {
        do {
            return try getStoredCredentials()?.rememberMe ?? false
        } catch {
            print("Could not retrieve remember-me setting: \(error)")
            return false
        }
    }



    func clearCredentials() throws {

        let query: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrService as String: service,
            kSecAttrAccount as String: account
        ]

        let status = SecItemDelete(query as CFDictionary)

        guard status == errSecSuccess ||
              status == errSecItemNotFound else {
            throw KeychainError.operationFailed(status)
        }
    }
}



enum KeychainError: LocalizedError {
    case operationFailed(OSStatus)

    var errorDescription: String? {
        switch self {
        case .operationFailed(let status):
            return "Keychain operation failed. Status: \(status)"
        }
    }
}
