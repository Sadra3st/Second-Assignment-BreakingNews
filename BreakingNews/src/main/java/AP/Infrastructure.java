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
import java.io.*;
import java.util.List;
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
        System.out.println("API Response: " + JSONRESULT);
        if (JSONRESULT != null) {
            parseInformation(); // 🚀 Parse articles immediately after getting data
        }
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
        if (JSONRESULT == null) {
            System.out.println("Error: No JSON data received.");
            return;
        }

        newsList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(JSONRESULT);
            JSONArray articles = jsonObject.getJSONArray("articles");

            for (int i = 0; i < Math.min(articles.length(), 20); i++) {
                JSONObject article = articles.getJSONObject(i);

                String title = article.optString("title", "No Title");
                String description = article.optString("description", "No Description");
                String sourceName = article.getJSONObject("source").optString("name", "Unknown Source");
                String author = article.optString("author", "Unknown");
                String url = article.optString("url", "#");
                String publishedAt = article.optString("publishedAt", "Unknown Date");

                News news = new News(title, description, sourceName, author, url, publishedAt);
                newsList.add(news);
            }

            System.out.println("Successfully parsed " + newsList.size() + " articles.");

        } catch (Exception e) {
            System.out.println("JSON Parsing Error: " + e.getMessage());
        }
    }
    public void displayNewsList() {
        if (newsList == null || newsList.isEmpty()) {
            System.out.println("No news articles available.");
            return;
        }

        System.out.println("\n📢 Available News Articles:");
        for (int i = 0; i < newsList.size(); i++) {
            System.out.println((i + 1) + ". " + newsList.get(i).getTitle());
        }
    }

    public void saveFavoriteArticle(News news) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("favorites.txt", true))) {
            writer.write(news.getFormattedNews() + "\n");
            System.out.println("Article saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving article: " + e.getMessage());
        }
    }

    public void loadFavoriteArticles() {
        File file = new File("favorites.txt");
        if (!file.exists()) {
            System.out.println("No favorite articles found.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            System.out.println("\nFavorite Articles:");
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading favorites: " + e.getMessage());
        }
    }
}
