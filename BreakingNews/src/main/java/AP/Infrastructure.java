import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.time.LocalDate;

public class Infrastructure {

    private final String URL;
    private final String APIKEY;
    private final String JSONRESULT;
    private ArrayList<News> newsList;

    public Infrastructure(String APIKEY) {
        this.APIKEY = APIKEY;
        this.URL = "https://newsapi.org/v2/everything?q=tesla&from=" + LocalDate.now().minusDays(1) + "&sortBy=publishedAt&apiKey=";
        this.JSONRESULT = getInformation();
        this.newsList = new ArrayList<>();
        parseInformation();
    }

    public ArrayList<News> getNewsList() {
        return newsList;
    }

    private String getInformation() {
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + APIKEY))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                throw new IOException("HTTP error code: " + response.statusCode());
            }
        } catch (Exception e) {
            System.out.println("!!Exception : " + e.getMessage());
        }
        return null;
    }

    private void parseInformation() {
        if (JSONRESULT != null) {
            JSONObject jsonObject = new JSONObject(JSONRESULT);
            JSONArray articles = jsonObject.getJSONArray("articles");

            for (int i = 0; i < Math.min(articles.length(), 20); i++) {
                JSONObject article = articles.getJSONObject(i);
                String title = article.getString("title");
                String description = article.getString("description");
                String sourceName = article.getJSONObject("source").getString("name");
                String author = article.getString("author");
                String url = article.getString("url");
                String publishedAt = article.getString("publishedAt");

                News news = new News(title, description, sourceName, author, url, publishedAt);
                newsList.add(news);
            }
        }
    }

    public void displayNewsList() {
        for (int i = 0; i < newsList.size(); i++) {
            System.out.println((i + 1) + ". " + newsList.get(i).getTitle());
        }

        System.out.println("Select an article to read more (1-" + newsList.size() + "): ");
        int choice = new java.util.Scanner(System.in).nextInt();

        if (choice > 0 && choice <= newsList.size()) {
            newsList.get(choice - 1).displayNews();
        } else {
            System.out.println("Invalid choice!");
        }
    }
    public void saveFavorite(News news) {
        try (FileWriter writer = new FileWriter("favorites.txt", true)) {
            writer.write(news.getTitle() + "|" + news.getDescription() + "|" +
                    news.getSourceName() + "|" + news.getAuthor() + "|" +
                    news.getUrl() + "|" + news.getPublishedAt() + "\n");
            System.out.println("Article saved to favorites!");
        } catch (IOException e) {
            System.out.println("Error saving favorite: " + e.getMessage());
        }
    }

    public void displayFavorites() {
        try {
            System.out.println("Your Favorite Articles:");
            Files.lines(Paths.get("favorites.txt")).forEach(line -> {
                String[] parts = line.split("\\|");
                News news = new News(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
                news.displayNews();
                System.out.println("-----------------------------");
            });
        } catch (IOException e) {
            System.out.println("No favorites found or error reading file: " + e.getMessage());
        }
    }
}
}