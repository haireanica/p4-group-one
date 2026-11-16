package app.domain;

/**
 * Represents the operational status of an asset in the OrlandoLendIT system.
 * 
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
