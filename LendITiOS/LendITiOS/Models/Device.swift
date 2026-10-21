//
//  Device.swift
//  LendITiOS
//
//  Created by Steven Petocz on 10/21/26.
//

import Foundation

struct Device: Identifiable, Codable {
    
    let id: Int
    var deviceType: String
    var centerLocation: String
    var status: String

}
