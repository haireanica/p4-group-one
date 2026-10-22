//
//  SampleDevices.swift
//  LendITiOS
//
//  Created by Steven Petocz on 10/21/26.
//

import Foundation

extension Device {
    static let sampleData: [Device] = [
        Device(
            id: "LAP-001",
            deviceType: "Laptop",
            centerLocation: "Office for Local Aassistance (OLA)",
            status: "Available"
            
        ),
        Device(
            id: "TAB-001",
            deviceType: "Tablet",
            centerLocation: "Office for Local Aassistance (OLA)",
            status: "Available"
        ),
        Device(
            id: "HOT-001",
            deviceType: "Hotspot",
            centerLocation: "Office for Local Aassistance (OLA)",
            status: "Available"
        )
    ]
}
