package AP;

public class News {
    private String title;
    private String description;
    private String sourceName;
    private String author;
    private String url;
    private String publishedAt;

    public News(String title, String description, String sourceName, String author, String url, String publishedAt) {
        this.title = title;
        this.description = description;
        this.sourceName = sourceName;
        this.author = (author == null || author.isEmpty()) ? "Unknown" : author;
        this.url = url;
        this.publishedAt = publishedAt;
    }

    public void displayNews() {
        System.out.println("\nTitle: " + title);
        System.out.println("Source: " + sourceName);
        System.out.println("Author: " + author);
        System.out.println("Published At: " + publishedAt);
        System.out.println("Description: " + description);
        System.out.println("Read more: " + url);
        System.out.println("---------------------------------------------------");
    }

    public String getTitle() {
        return title;
    }
}
