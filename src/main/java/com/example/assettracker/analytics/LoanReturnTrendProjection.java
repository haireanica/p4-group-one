package com.example.assettracker.analytics;

public interface LoanReturnTrendProjection {
    String getDate();
    long getLoansCount();
    long getReturnsCount();
}
