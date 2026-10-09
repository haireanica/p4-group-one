/**
 * The loanStats enum class will be used to track the status of a loan in OrlandoLendIT.
 * The values are defined in the loanStats enum class.
 */
package app.domain;

public enum loanStats {

    /**
     * The loan is currently checked out and is not yet returned.
     */
    CHECKED_OUT,

    /**
     * The loan has been returned.
     */
    CHECKED_IN,

    /**
     * The loan is ready to be checked out.
     */
    READY,
}
