//
//  SampleParticipants.swift
//  LendITiOS
//
//  Created by Steven Petocz on 10/21/26.
//

import Foundation

extension Participant {
    static let sampleData: [Participant] = [
        Participant(
            id: 1,
            firstName: "John",
            lastName: "Smith",
            phoneNumber: "407-555-0123",
            addressLine1: "123 Main Street",
            addressLine2: "",
            city: "Orlando",
            zipcode: "32801"
        ),
        Participant(
            id: 2,
            firstName: "Jane",
            lastName: "Doe",
            phoneNumber: "407-555-0145",
            addressLine1: "456 Oak Avenue",
            addressLine2: "Apt 2B",
            city: "Kissimmee",
            zipcode: "34741"
        ),
        Participant(
            id: 3,
            firstName: "Michael",
            lastName: "Johnson",
            phoneNumber: "407-555-0178",
            addressLine1: "789 Lake Street",
            addressLine2: "",
            city: "Sanford",
            zipcode: "32771"
        )
    ]
}
