public class Verse {
    private String author;
    private String verse;
    private String title;
    private int date;
    private String century;
    private String literatureProblem;
    private String foot;


    public Verse(String title, String author) {
        this.author = author;
        this.title = title;
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

    public String getCentury() {
        return century;
    }

    public void setCentury(String century) {
        this.century = century;
    }


    public String getLiteratureProblem() {
        return literatureProblem;
    }

    public void setLiteratureProblem(String literatureProblem) {
        this.literatureProblem = literatureProblem;
    }

    public String getFoot() {
        return foot;
    }

    public void setFoot(String foot) {
        this.foot = foot;
    }

    
}
