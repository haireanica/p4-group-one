

import Foundation

struct LoanDto: Codable {
    let loanId: Int?
    let assetId: String?
    let assetName: String?
    let center: String?
    let participantId: Int?
    let reason: String?
    let loanPurpose: String?
    let checkoutDate: String?
    let dueDate: String?
    let returnDate: String?
    let status: String?

    func toLoan() -> Loan {
        return Loan(
            id: loanId.map { String($0) } ?? "",
            deviceType: assetName ?? "",
            centerLocation: center ?? "",
            loanStartDate: checkoutDate ?? "",
            loanDueDate: dueDate ?? "",
            participantId: participantId
        )
    }
}
