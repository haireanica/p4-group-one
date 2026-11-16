package app.analytics;

public class DeviceStatusDistributionDTO {
    private String status;
    private long count;

    public DeviceStatusDistributionDTO(String status, long count) {
        this.status = status;
        this.count = count;
    }

    public String getStatus() { return status; }
    public long getCount() { return count; }
}