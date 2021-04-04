import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Locale;

public class Parser {
    //    https://www.culture.ru/literature/poems?query=зимнее+утро
    // Literature of modern times gold silver
//    private final String SOURCE = "https://obrazovaka.ru/analiz-stihotvoreniya";
    private Document document;


    public Verse parse(String title, String author) {
        Verse verse = new Verse(title, author);
        try {
            setInfoFromFirstResource(verse);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
//        try {
//            document = Jsoup.connect(SOURCE).userAgent("Chrome/4.0.249.0 Safari/532.5").referrer("http://www.google.com").get();
//            try {
//                String linkVerse = searchVerseInSecondSource(verse, author);
//                document = Jsoup.connect(linkVerse).userAgent("Chrome/4.0.249.0 Safari/532.5").referrer("http://www.google.com").get();
//                Elements listVerses = document.select("div#entity-cards entity-cards__spacing__reset grid-1-noSpaceTop_notebook-3_tablet-small-2_mobile-medium-1");
//            } catch (Exception e) {
//
//            }
//
//        } catch (IOException e) {
//
//        }
        return verse;
    }

    private void setInfoFromFirstResource(Verse verse) throws Exception {
        System.out.println(searchVerseInFirstSource(verse));
        String link = "https://www.culture.ru" + searchVerseInFirstSource(verse);
        setVerse(link, verse);
        setDate(link, verse);
    }

    private String searchVerseInFirstSource(Verse verse) throws Exception {
        String source = "https://www.culture.ru/literature/poems?query=" + verse.getTitle().replace(' ', '+').toLowerCase(Locale.ROOT);
        document = Jsoup.connect(source).userAgent("Chrome/4.0.249.0 Safari/532.5").referrer("http://www.google.com").get();
        Elements listVerses = document.select("div.card-heading_head");
        for (Element element : listVerses.select("div.card-heading_head")) {
            if (element.select("a.card-heading_subtitle").text().toLowerCase(Locale.ROOT).contains(verse.getAuthor().toLowerCase(Locale.ROOT))
                    && element.select("a.card-heading_title-link").text().toLowerCase(Locale.ROOT).contains(verse.getTitle().toLowerCase(Locale.ROOT))) {
                return element.select("a.card-heading_title-link").attr("href");
            }
        }
        throw new Exception("Verse not found!");
    }

    private void setVerse(String source, Verse verse) throws IOException {
        document = Jsoup.connect(source).userAgent("Chrome/4.0.249.0 Safari/532.5").referrer("http://www.google.com").get();
        Elements listVerses = document.select("div.content-columns_block");
        StringBuilder builder = new StringBuilder();
        for (Element element : listVerses.select("p")) {
            builder.append(element.text());
        }
        System.out.println(builder.toString());
        verse.setVerse(builder.toString());
    }

    private void setDate(String source, Verse verse) throws IOException {
        document = Jsoup.connect(source).userAgent("Chrome/4.0.249.0 Safari/532.5").referrer("http://www.google.com").get();
        Elements listVerses = document.select("div.content-columns_footer");
        String date = listVerses.text();
        if (!"".equals(date)) {
            verse.setDate(Integer.parseInt(date.substring(0, date.length() - 3)));
            System.out.println(verse.getDate());
        }
    }

//    private String searchVerseInSecondSource(String verse, String author) throws Exception {
//        Elements listVerses = document.select("div#content-main.full-width");
//        for (Element element : listVerses.select("a")) {
//            if (element.text().contains(verse) && element.text().contains(author)) {
//                return element.attr("abs:href");
//            }
//        }
//        throw new Exception("Verse not found!");
//    }
}
