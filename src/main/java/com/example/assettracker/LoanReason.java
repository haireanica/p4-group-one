package com.example.assettracker;

// A fixed list instead of a free-text field: staff can still report on
// demand by need, but nobody can type sensitive details into it.
public enum LoanReason {
    EMPLOYMENT,
    EDUCATION,
    BENEFITS,
    TELEHEALTH,
    OTHER
}
