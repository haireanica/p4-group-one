
package p4groupone.lenditandroid.network

data class AssetDto(
    val assetId: String?,
    val name: String?,
    val deviceType: String?,
    val status: String?,
    val inventory: InventoryDto?
)

data class InventoryDto(
    val inventoryId: Int?,
    val center: CenterDto?
)

data class CenterDto(
    val centerId: Int?,
    val name: String?,
    val addressLine1: String?,
    val addressLine2: String?,
    val city: String?,
    val state: String?,
    val postalCode: String?
)
