
import Foundation



enum APIError: LocalizedError {
    case invalidURL
    case invalidResponse
    case serverError(statusCode: Int, message: String)

    var errorDescription: String? {
        switch self {
        case .invalidURL:
            return "The server URL is invalid."

        case .invalidResponse:
            return "The server returned an invalid response."

        case .serverError(let statusCode, let message):
            return "Error \(statusCode): \(message)"
        }
    }
}



final class APIService {

    static let shared = APIService()

    private let baseURL = "http://localhost:8081"

    private init() {}



    private func makeRequest(
        path: String,
        method: String
    ) throws -> URLRequest {

        guard let url = URL(string: "\(baseURL)/\(path)") else {
            throw APIError.invalidURL
        }

        var request = URLRequest(url: url)

        request.httpMethod = method

        request.setValue(
            "application/json",
            forHTTPHeaderField: "Content-Type"
        )

        request.setValue(
            "application/json",
            forHTTPHeaderField: "Accept"
        )

        return request
    }

    private func makeRequest<Body: Encodable>(
        path: String,
        method: String,
        body: Body
    ) throws -> URLRequest {

        var request = try makeRequest(
            path: path,
            method: method
        )

        request.httpBody = try JSONEncoder().encode(body)

        return request
    }

 

    private func perform<T: Decodable>(
        _ request: URLRequest
    ) async throws -> T {

        let (data, response) = try await APIClient.shared.session.data(
            for: request
        )

        guard let httpResponse = response as? HTTPURLResponse else {
            throw APIError.invalidResponse
        }

        guard (200...299).contains(httpResponse.statusCode) else {

            let message = String(
                data: data,
                encoding: .utf8
            ) ?? "An unexpected server error occurred."

            throw APIError.serverError(
                statusCode: httpResponse.statusCode,
                message: message
            )
        }

        return try JSONDecoder().decode(
            T.self,
            from: data
        )
    }

    private func performNoContent(
        _ request: URLRequest
    ) async throws {

        let (_, response) = try await APIClient.shared.session.data(
            for: request
        )

        guard let httpResponse = response as? HTTPURLResponse else {
            throw APIError.invalidResponse
        }

        guard (200...299).contains(httpResponse.statusCode) else {
            throw APIError.serverError(
                statusCode: httpResponse.statusCode,
                message: "The request could not be completed."
            )
        }
    }



    func login(
        request: LoginRequest
    ) async throws -> LoginResponse {

        let urlRequest = try makeRequest(
            path: "api/users/login",
            method: "POST",
            body: request
        )

        return try await perform(urlRequest)
    }

    func logout() async throws {

        let urlRequest = try makeRequest(
            path: "api/users/logout",
            method: "POST"
        )

        try await performNoContent(urlRequest)

       
        APIClient.shared.clearSession()
    }


    func getLoanees() async throws -> [LoaneeDto] {

        let request = try makeRequest(
            path: "api/loanees",
            method: "GET"
        )

        return try await perform(request)
    }

    func registerLoanee(
        request: LoaneeRegistration
    ) async throws -> LoaneeDto {

        let urlRequest = try makeRequest(
            path: "api/loanees",
            method: "POST",
            body: request
        )

        return try await perform(urlRequest)
    }

    func updateLoanee(
        id: Int,
        request: LoaneeRegistration
    ) async throws -> LoaneeDto {

        let urlRequest = try makeRequest(
            path: "api/loanees/\(id)",
            method: "PUT",
            body: request
        )

        return try await perform(urlRequest)
    }


    func getAssets() async throws -> [AssetDto] {

        let request = try makeRequest(
            path: "api/assets",
            method: "GET"
        )

        return try await perform(request)
    }

    func getLoans() async throws -> [LoanDto] {

        let request = try makeRequest(
            path: "api/loans",
            method: "GET"
        )

        return try await perform(request)
    }

    func checkoutLoan(
        request: LoanRequest
    ) async throws -> LoanDto {

        let urlRequest = try makeRequest(
            path: "api/loans/checkout",
            method: "POST",
            body: request
        )

        return try await perform(urlRequest)
    }

    func returnLoan(
        loanId: Int,
        request: ReturnRequest
    ) async throws -> LoanDto {

        let urlRequest = try makeRequest(
            path: "api/loans/\(loanId)/return",
            method: "POST",
            body: request
        )

        return try await perform(urlRequest)
    }
}
