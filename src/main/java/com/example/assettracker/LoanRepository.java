package com.example.assettracker;

import com.example.assettracker.analytics.LoanReturnTrendProjection;
import com.example.assettracker.analytics.OverdueLoansByCenterDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    long count();
    long countByLoanStatus(String loanStatus);

    @Query("SELECT new com.example.assettracker.analytics.OverdueLoansByCenterDTO(c.name, COUNT(l)) " +
            "FROM Loan l " +
            "JOIN l.asset a " +
            "JOIN a.inventory i " +
            "JOIN i.center c " +
            "WHERE l.loanStatus = 'OVERDUE' " +
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
