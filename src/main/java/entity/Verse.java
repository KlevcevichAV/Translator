package entity;

import java.util.HashMap;

public class Verse {
    private String author;
    private String verse;
    private String title;
    private int date;
    private String foot;
    private String century;
    private HashMap<String, String> tropes;


    public Verse(String title, String author) {
        this.author = author;
        this.title = title;
        tropes = new HashMap<>();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getVerse() {
        return verse;
    }

    public void setVerse(String verse) {
        this.verse = verse;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getFoot() {
        return foot;
    }

    public void setFoot(String foot) {
        this.foot = foot;
    }

    public HashMap<String, String> getTropes() {
        return tropes;
    }

    public void setTropes(HashMap<String, String> tropes) {
        this.tropes = tropes;
    }

    public String getCentury() {
        return century;
    }

    public void setCentury(String century) {
        this.century = century;
    }

    @Override
    public String toString() {
        return "entity.Verse{" +
                "author='" + author + '\'' +
                ", verse='" + verse + '\'' +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", foot='" + foot + '\'' +
                ", tropes=" + tropes +
                '}';
    }
}
