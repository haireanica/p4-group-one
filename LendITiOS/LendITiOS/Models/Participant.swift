//
//  Participant.swift
//  LendITiOS
//
//  Created by Steven Petocz on 10/21/26.
//

import Foundation

struct Participant: Identifiable, Codable {
    let id: Int
    var firstName: String
    var lastName: String
    var phoneNumber: String
    var addressLine1: String
    var addressLine2: String?
    var city: String
    var zipcode: String

    var fullName: String {
        "\(firstName) \(lastName)"
    }
}
