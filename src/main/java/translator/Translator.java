package translator;

import entity.Verse;

import java.io.IOException;

public class Translator {
    private final TranslatorToSCS translatorToSCS;
    private final TranslatorToHTML translatorToHTML;

    public Translator() {
        translatorToSCS = new TranslatorToSCS();
        translatorToHTML = new TranslatorToHTML();
    }

    public void translate(Verse verse) throws IOException {
        translatorToSCS.translate(verse);
        translatorToHTML.translate(verse);
        System.out.println(translatorToSCS.getFile());
        System.out.println(translatorToHTML.getFile());
    }
}
