
public class Main {
    public static void main(String[] args) {
        try {
            Parser parser = new Parser();
            Verse verse = parser.parse("Зимнее утро", "Пушкин");
            Translate translate = new Translate();
            System.out.println(translate.translate( "Зимнее утро"));
            System.out.println(translate.translate( "Пушкин"));
            System.out.println(translate.translate( "Умом Россию не понять"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
