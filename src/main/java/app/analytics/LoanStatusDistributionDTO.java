package app.analytics;

public class LoanStatusDistributionDTO {
    private String status;
    private long count;

    public LoanStatusDistributionDTO(String status, long count) {
        this.status = status;
        this.count = count;
    }

    public String getStatus() { return status; }
    public long getCount() { return count; }
}