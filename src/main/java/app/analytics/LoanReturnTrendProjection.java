package app.analytics;

import java.time.LocalDate;

public interface LoanReturnTrendProjection {
    LocalDate getDate();
    Long getLoansCount();
    Long getReturnsCount();
}
