//
//  Loan.swift
//  LendITiOS
//
//  Created by Steven Petocz on 10/22/26.
//

import Foundation

struct Loan: Identifiable, Codable {
    
    let id: String
    var deviceType: String
    var centerLocation: String
    var loanStartDate: String
    var loanDueDate: String

}
