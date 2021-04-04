import entity.Verse;
import parser.Parser;
import translator.TranslatorToHTML;

public class Main {
    public static void main(String[] args) {
        try {
            Parser parser = new Parser();
            Verse verse = parser.parse("Зимнее утро", "Пушкин");
            System.out.println(verse);
            TranslatorToHTML translator = new TranslatorToHTML();
            translator.translate(verse);
            System.out.println(translator.getFile());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
