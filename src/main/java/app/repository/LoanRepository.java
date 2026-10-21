package app.repository;

import app.analytics.LoanReturnTrendDTO;
import app.domain.Loan;
import app.domain.loanStats;
import app.analytics.LoanReturnTrendProjection;
import app.analytics.OverdueLoansByCenterDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

/**
 * Provides database operations for {@link Loan} records.
 */
public interface LoanRepository extends JpaRepository<Loan, Integer> {
    long count();

    // Spring Data JPA automatically maps this using the loanStats enum
    long countByLoanStatus(loanStats loanStatus);

    @Query("SELECT COUNT(l) FROM Loan l WHERE l.loanStatus = app.domain.loanStats.CHECKED_OUT AND l.dueDate < CURRENT_DATE")
    long countOverdueLoans();

    @Query("SELECT new app.analytics.OverdueLoansByCenterDTO(c.name, COUNT(l)) " +
            "FROM Loan l " +
            "JOIN l.assetId a " +
            "JOIN a.inventory i " +
            "JOIN i.center c " +
            "WHERE l.loanStatus = app.domain.loanStats.CHECKED_OUT AND l.dueDate < CURRENT_DATE " +
            "GROUP BY c.name")
    List<OverdueLoansByCenterDTO> getOverdueLoansByCenter();

    @Query(value = """
            SELECT 
                d.date AS date,
                SUM(CASE WHEN l.checkoutDate = d.date THEN 1 ELSE 0 END) AS loansCount,
                SUM(CASE WHEN l.returnDate = d.date THEN 1 ELSE 0 END) AS returnsCount        
            FROM loan l CROSS JOIN (
                SELECT DISTINCT checkoutDate AS date FROM loan WHERE checkoutDate IS NOT NULL
                UNION SELECT DISTINCT returnDate AS date FROM loan WHERE returnDate IS NOT NULL
            ) d
            GROUP BY d.date
            ORDER BY d.date ASC                    
            """, nativeQuery = true)
    List<LoanReturnTrendProjection> getLoanReturnTrends();
}