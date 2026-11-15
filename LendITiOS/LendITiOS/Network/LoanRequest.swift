

import Foundation

struct LoanRequest: Codable {
    let assetId: String
    let participantId: Int
    let reason: String
    let loanPurpose: String
    let days: Int
}
