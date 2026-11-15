
import Foundation

struct LoaneeDto: Codable {

    let participantId: Int?
    let firstName: String?
    let lastName: String?
    let phoneNumber: String?
    let addressLine1: String?
    let addressLine2: String?
    let city: String?
    let postalCode: String?
    let state: String?

    func toParticipant() -> Participant {
        Participant(
            id: participantId ?? 0,
            firstName: firstName ?? "",
            lastName: lastName ?? "",
            phoneNumber: phoneNumber ?? "",
            addressLine1: addressLine1 ?? "",
            addressLine2: addressLine2 ?? "",
            city: city ?? "",
            state: state ?? "FL",
            zipcode: postalCode ?? ""
        )
    }
}
