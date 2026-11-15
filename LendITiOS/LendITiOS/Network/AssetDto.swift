

import Foundation


struct AssetDto: Codable {

    let assetId: String?
    let name: String?
    let deviceType: String?
    let status: String?
    let inventory: InventoryDto?

    func toDevice() -> Device {
        Device(
            id: assetId ?? "",
            deviceType: deviceType ?? name ?? "",
            centerLocation: inventory?.center?.name ?? "",
            status: status ?? ""
        )
    }
}


struct InventoryDto: Codable {

    let inventoryId: Int?
    let center: CenterDto?
}


struct CenterDto: Codable {

    let centerId: Int?
    let name: String?
    let addressLine1: String?
    let addressLine2: String?
    let city: String?
    let state: String?
    let postalCode: String?
}
