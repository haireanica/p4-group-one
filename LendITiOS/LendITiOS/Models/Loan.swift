import Foundation

struct Loan: Identifiable, Codable {
    let id: String
    var deviceType: String
    var centerLocation: String
    var loanStartDate: String
    var loanDueDate: String
    var participantId: Int? = nil
}
