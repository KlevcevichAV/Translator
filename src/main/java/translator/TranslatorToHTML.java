package translator;

import entity.Verse;

import java.io.IOException;
import java.util.Map;

public class TranslatorToHTML {

    String file;

    public void translate(Verse verse) throws IOException {
        file = verse.getVerse();
        for (Map.Entry<String, String> entry : verse.getTropes().entrySet()) {
            addSCElement(entry);
        }
    }

    private void addSCElement(Map.Entry<String, String> trope) throws IOException {
        String SCElement = String.format("<sc_element sys_idtf = \"%s\">%s</sc_element>", Translate.translate(trope.getValue()), trope.getKey());
        file = file.replaceAll(trope.getKey(), SCElement);
    }

    public String getFile() {
        return file;
    }
}
