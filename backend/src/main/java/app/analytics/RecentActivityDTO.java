package app.analytics;

public class RecentActivityDTO {
    private String title;
    private String description;
    private String timeAgo;

    public RecentActivityDTO(String title, String description, String timeAgo) {
        this.title = title;
        this.description = description;
        this.timeAgo = timeAgo;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getTimeAgo() { return timeAgo; }
}