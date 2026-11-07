package app.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.analytics.LoanReturnTrendProjection;
import app.domain.Loan;
import app.domain.loanStats;

/**
 * Provides database operations for {@link Loan} records.
 */
public interface LoanRepository extends JpaRepository<Loan, Integer> {

    long count();

    long countByLoanStatus(loanStats loanStatus);

    @Query("SELECT COUNT(l) FROM Loan l WHERE l.loanStatus = app.domain.loanStats.CHECKED_OUT AND l.dueDate < CURRENT_DATE AND l.returnDate IS NULL")
    long countOverdueLoans();

    @Query(value = """
    SELECT 
        DAYNAME(d.dt) AS dayOfWeek,
        COALESCE(SUM(CASE WHEN l.checkoutDate = d.dt THEN 1 ELSE 0 END), 0) + 
        COALESCE(SUM(CASE WHEN l.returnDate = d.dt THEN 1 ELSE 0 END), 0) AS totalLoanActivity
    FROM (
        SELECT CURDATE() - INTERVAL t.i DAY AS dt
        FROM (
            SELECT 0 AS i UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6
        ) t
    ) d
    LEFT JOIN loan l ON (l.checkoutDate = d.dt OR l.returnDate = d.dt)
    GROUP BY d.dt, dayOfWeek
    ORDER BY d.dt ASC
    """, nativeQuery = true)
    List<LoanReturnTrendProjection> getCurrentWeekLoanActivity();

    @Query("SELECT l FROM Loan l ORDER BY COALESCE(l.returnDate, l.checkoutDate) DESC")
    List<Loan> findRecentLoans(Pageable pageable);
}