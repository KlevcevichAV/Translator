import entity.Verse;
import parser.Parser;
import translator.Translate;
import translator.Translator;

public class Main {
    public static void main(String[] args) {
        try {
            Parser parser = new Parser();
            Verse verse = parser.parse("Зимнее утро", "Пушкин");
            System.out.println(Translate.translate("Умом россию не понять"));
            Translator translator = new Translator();
            translator.translate(verse);
            System.out.println(verse);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
