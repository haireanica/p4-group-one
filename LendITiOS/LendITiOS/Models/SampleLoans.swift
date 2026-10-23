//
//  SampleLoans.swift
//  LendITiOS
//
//  Created by Steven Petocz on 10/22/26.
//

import Foundation

extension Loan {
    static let sampleData: [Loan] = [
        Loan(
            id: "L-001",
            deviceType: "Laptop",
            centerLocation: "Office for Local Aassistance (OLA)",
            loanStartDate: "10-22-2026",
            loanDueDate: "10-29-2026"
        ),
        Loan(
            id: "L-002",
            deviceType: "Tablet",
            centerLocation: "Office for Local Aassistance (OLA)",
            loanStartDate: "10-22-2026",
            loanDueDate: "10-29-2026"
        ),
        Loan(
            id: "L-003",
            deviceType: "Hotspot",
            centerLocation: "Office for Local Aassistance (OLA)",
            loanStartDate: "10-22-2026",
            loanDueDate: "10-29-2026"
        )
    ]
}
