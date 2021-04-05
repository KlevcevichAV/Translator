package translator;

import entity.Verse;

import java.util.Map;

public class TranslatorToHTML {

    private String file;

    public void translate(Verse verse) {
        file = verse.getVerse();
        for (Map.Entry<String, String> entry : verse.getTropes().entrySet()) {
            addSCElement(entry);
        }
    }

    private void addSCElement(Map.Entry<String, String> trope) {
        String SCElement = String.format("<sc_element sys_idtf = \"%s\">%s</sc_element>", trope.getValue(), trope.getKey());
        file = file.replaceAll(trope.getKey(), SCElement);
    }

    public String getFile() {
        return file;
    }
}
