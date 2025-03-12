package AP;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class News implements Serializable {
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
        this.publishedAt = convertToHijri(publishedAt); // Convert date to Hijri format
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getFormattedNews() {
        return title + " | " + sourceName + " | " + publishedAt + " | " + url;
    }

        private String convertToHijri(String gregorianDate) {
            try {
                LocalDate date = LocalDate.parse(gregorianDate, DateTimeFormatter.ISO_DATE_TIME);
                int[] g2j = gregorianToJalali(date.getYear(), date.getMonthValue(), date.getDayOfMonth());

                return g2j[0] + "/" + g2j[1] + "/" + g2j[2];
            } catch (Exception e) {
                return gregorianDate; // Return original if conversion fails
            }
        }

        private int[] gregorianToJalali(int gYear, int gMonth, int gDay) {
            int[] gDaysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            int[] jDaysInMonth = {31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29};

            int gDayNo, jDayNo, jYear, jMonth, jDay;
            boolean leap = (gYear % 4 == 0 && gYear % 100 != 0) || (gYear % 400 == 0);

            if (leap) gDaysInMonth[1] = 29;

            gDayNo = (gYear - 1600) * 365 + ((gYear - 1600 + 3) / 4) - ((gYear - 1600 + 99) / 100) + ((gYear - 1600 + 399) / 400);

            for (int i = 0; i < gMonth - 1; i++) {
                gDayNo += gDaysInMonth[i];
            }

            gDayNo += gDay - 1;
            jDayNo = gDayNo - 79;
            int jNp = jDayNo / 12053;

            jDayNo %= 12053;
            jYear = 979 + 33 * jNp + 4 * (jDayNo / 1461);
            jDayNo %= 1461;

            if (jDayNo >= 366) {
                jYear += (jDayNo - 1) / 365;
                jDayNo = (jDayNo - 1) % 365;
            }

            for (jMonth = 0; jMonth < 11 && jDayNo >= jDaysInMonth[jMonth]; jMonth++) {
                jDayNo -= jDaysInMonth[jMonth];
            }

            jDay = jDayNo + 1;
            return new int[]{jYear, jMonth + 1, jDay};
        }


    public void displayNews() {
        System.out.println("\nTitle: " + title);
        System.out.println("Source: " + sourceName);
        System.out.println("Author: " + author);
        System.out.println("Published At (Hijri): " + publishedAt);
        System.out.println("Description: " + description);
        System.out.println("Read more: " + url);
        System.out.println("---------------------------------------------------");
    }
}
