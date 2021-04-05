import entity.Verse;
import parser.Parser;
import translator.Translator;

public class Main {
    public static void main(String[] args) {
        try {
            Parser parser = new Parser();
            Verse verse = parser.parse("Бородино", "Лермонтов");
            Translator translator = new Translator();
            translator.translate(verse);
            System.out.println(verse);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
