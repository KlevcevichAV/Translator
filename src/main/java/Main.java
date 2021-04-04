public class Main {
    public static void main(String[] args) {
        try {
            Parser parser = new Parser();
            Verse verse = parser.parse("Зимнее утро", "Пушкин");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
