import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public class Parser {
    private Document document;
    private Elements elements;


    public Verse parse(String title, String author) throws Exception {
        Verse verse = new Verse(title, author);
        setInfoFromFirstResource(verse);
        try {
            setInfoFromSecondResource(verse);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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

    private void setInfoFromSecondResource(Verse verse) throws Exception {
        String source = "https://obrazovaka.ru/analiz-stihotvoreniya";
        document = Jsoup.connect(source).userAgent("Chrome/4.0.249.0 Safari/532.5").referrer("http://www.google.com").get();
        String linkVerse = searchVerseInSecondSource(verse);
        document = Jsoup.connect(linkVerse).userAgent("Chrome/4.0.249.0 Safari/532.5").referrer("http://www.google.com").get();
        elements = document.select("div.kratkiy-analiz");
        setFoot(verse);
        setTropes(verse);
        System.out.println(verse.getFoot());
    }

    private String searchVerseInSecondSource(Verse verse) throws Exception {
        Elements listVerses = document.select("div#content-main.full-width");
        for (Element element : listVerses.select("a")) {
            if (element.text().contains(verse.getTitle()) && element.text().contains(verse.getAuthor())) {
                return element.attr("abs:href");
            }
        }
        throw new Exception("Verse not found!");
    }

    private void setFoot(Verse verse) {
        List<String> foots = Arrays.asList("ямб", "хорей", "амфибрахий", "анапест", "дактиль", "четырехсложный размер", "пятисложный размер", "акцентный размер");
        Elements analise = elements.select("p");
        for (Element element : analise) {
            if (element.text().contains("Стихотворный размер")) {
                for (String foot : foots) {
                    if (element.text().contains(foot)) {
                        verse.setFoot(foot);
                        return;
                    }
                }
            }
        }
    }

    private void setTropes(Verse verse) {
        List<String> tropes = Arrays.asList("литот", "метафор", "эпитет", "параллелизм", "аллегори", "ирони", "сравнени", "олицетворени", "гипербол", "перифраз", "оксюморон", "лексический повтор", "синтаксический параллелизм", "инверси", "пафос", "эвфемизм", "синекдоха", "дисфемизм", "метоними", "сарказм", "каламбур");
        Elements analise = elements.select("p");
        for (Element element : analise) {

            for (String trope : tropes) {
                if (element.text().toLowerCase(Locale.ROOT).contains(trope)) {
//                        System.out.println(element.text());
                    System.out.println(trope);
                    Arrays.stream(Stream.of(element.text().split("–")).skip(1)
                            .flatMap((p) -> Arrays.stream(p.split(","))).toArray(String[]::new))
                            .map(p -> p.replaceAll("\\.", ""))
                            .map(p -> p.replaceAll("”", ""))
                            .map(p -> p.replaceAll(" “", ""))
                            .forEach(el -> verse.getTropes().put(el, trope));
                }
            }

        }
        System.out.println(verse.getTropes());
    }
}
