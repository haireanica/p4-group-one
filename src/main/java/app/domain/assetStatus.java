/**
 * The assetStatus enum class will be used to track the status of an asset in OrlandoLendIT.
 * The values are defined in the assetStatus enum class.
 */
package app.domain;

/**
 * Represents the operational status of an asset in the OrlandoLendIT system.
 * assetStatus
 */

public enum assetStatus {

    /**
     * The asset is available for checkout and is not currently assigned to a loan or undergoing maintenance.
     */
    AVAILABLE,

    /**
     * The asset is currently assinged to a loan and is not available for checkout.
     */
    CHECKED_OUT,

    /**
     * The asset is undergoing maintenance.
     */
    IN_MAINTENANCE,

    /**
     * The asset is permanently removed from service.
     */
    RETIRED,


}
