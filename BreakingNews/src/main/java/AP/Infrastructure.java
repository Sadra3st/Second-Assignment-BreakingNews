package AP;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Scanner;

public class Infrastructure {
    private final String URL;
    private final String APIKEY;
    private final String JSONRESULT;
    private ArrayList<News> newsList;

    public Infrastructure(String APIKEY) {
        this.APIKEY = APIKEY;
        this.URL = "https://newsapi.org/v2/everything?q=tesla&from=2025-02-05&sortBy=publishedAt&apiKey=";
        this.newsList = new ArrayList<>();
        this.JSONRESULT = getInformation();
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
        if (JSONRESULT == null || JSONRESULT.isEmpty()) {
            System.out.println("No data received from API.");
            return;
        }

        JSONObject jsonObject = new JSONObject(JSONRESULT);
        JSONArray articles = jsonObject.getJSONArray("articles");

        for (int i = 0; i < Math.min(20, articles.length()); i++) {
            JSONObject article = articles.getJSONObject(i);
            String title = article.optString("title", "No Title");
            String description = article.optString("description", "No Description");
            String sourceName = article.getJSONObject("source").optString("name", "Unknown Source");
            String author = article.optString("author", "Unknown");
            String url = article.optString("url", "No URL");
            String publishedAt = article.optString("publishedAt", "Unknown Date");

            News news = new News(title, description, sourceName, author, url, publishedAt);
            newsList.add(news);
        }
    }

    public void displayNewsList() {
        if (newsList.isEmpty()) {
            System.out.println("No news articles available.");
            return;
        }

        System.out.println("\nAvailable News Articles:");
        for (int i = 0; i < newsList.size(); i++) {
            System.out.println((i + 1) + ". " + newsList.get(i).getTitle());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter the number of the article to read more (0 to exit): ");
        int choice = scanner.nextInt();

        if (choice > 0 && choice <= newsList.size()) {
            newsList.get(choice - 1).displayNews();
        } else {
            System.out.println("Invalid choice. Exiting...");
        }
    }
}
