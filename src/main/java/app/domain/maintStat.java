/**
 * The maintStat enum class will be used to track the status of a maintenance task in OrlandoLendIT.
 * The values are defined in the maintStat enum class.
 */
package app.domain;

public enum maintStat {


    /**
     * The maintenance task is currently in progress and has not yet been completed.
     */
    IN_PROGRESS,

    /**
     * The maintenance task has been completed and the asset is ready for use.
     */
    COMPLETED,

    /**
     * The maintenance task has been reported as broken and is not yet completed.
     */
    BROKEN,

}
