package app.domain;

/**
 * Represents the lifecycle of a loan in the system.
 */
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
