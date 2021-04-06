import entity.Verse;
import parser.Parser;
import translator.Translator;

public class Main {
    public static void main(String[] args) {
        try {
            Parser parser = new Parser();
            Verse verse = parser.parse("Песня про сражение на реке Черной 4 августа 1855", "Толстой");
            Translator translator = new Translator();
            translator.translate(verse);
            System.out.println(verse);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
