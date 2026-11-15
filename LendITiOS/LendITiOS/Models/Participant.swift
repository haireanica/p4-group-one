
import Foundation

struct Participant: Identifiable, Codable, Hashable {
    let id: Int
    var firstName: String
    var lastName: String
    var phoneNumber: String
    var addressLine1: String
    var addressLine2: String
    var city: String
    var state: String
    var zipcode: String

    var fullName: String {
        "\(firstName) \(lastName)"
    }
}
