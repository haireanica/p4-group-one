package app.analytics;

public class LoanReturnTrendDTO {
    private String dayOfWeek;
    private long totalLoanActivity;

    public LoanReturnTrendDTO(String dayOfWeek, long totalLoanActivity) {
        this.dayOfWeek = dayOfWeek;
        this.totalLoanActivity = totalLoanActivity;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public long getTotalLoanActivity() {
        return totalLoanActivity;
    }
}