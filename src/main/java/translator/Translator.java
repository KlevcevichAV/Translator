package translator;

import config.Config;
import entity.Verse;

import java.io.*;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;

public class Translator {
    private final TranslatorToSCS translatorToSCS;
    private final TranslatorToHTML translatorToHTML;
    private final String GOLDEN_AGE = "Золотой век";
    private final String SILVER_AGE = "Серебряный век";

    public Translator() {
        translatorToSCS = new TranslatorToSCS();
        translatorToHTML = new TranslatorToHTML();
    }

    public void translate(Verse verse) {
        try {
            translatorToSCS.translate(verse);
            translatorToHTML.translate(verse);
            String path = setPath(verse);
            String pathPoetry = path + (Objects.nonNull(verse.getCentury()) ? "/poetry" : "");
            writeCSCInFile(verse, pathPoetry);
            writeHTMLInFile(verse, pathPoetry);
            if (path.contains(Config.GOLDEN_AGE_PATH) || path.contains(Config.SILVER_AGE_PATH)) {
                addVerseInSectionSCS(path);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void writeHTMLInFile(Verse verse, String path) throws IOException {
        new File(path + "/content/").mkdir();
        try (FileWriter writer = new FileWriter(String.format("%s/content/%s.html", path, translatorToSCS.getMainIdtf()))) {
            writer.write(translatorToHTML.getFile());
        }
    }

    private void writeCSCInFile(Verse verse, String path) throws IOException {
        new File(path).mkdir();
        File file = new File(String.format("%s/%s.scs", path, translatorToSCS.getMainIdtf()));
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(translatorToSCS.getFile());
        }
    }

    private String setPath(Verse verse) throws IOException {
        if (GOLDEN_AGE.equals(verse.getCentury())) {
            return Config.GOLDEN_AGE_PATH;
        }
        if (SILVER_AGE.equals(verse.getCentury())) {
            return Config.SILVER_AGE_PATH;
        }
        return String.format("%s/%s", Config.DEFAULT_PATH, translatorToSCS.getMainIdtf());
    }

    private String convertTitle(String title) throws IOException {
        return Translate.translate(title).toLowerCase(Locale.ROOT).replaceAll(" ", "_");
    }

    private void addVerseInSectionSCS(String path) throws IOException {
        String name = path.substring(path.lastIndexOf("/") + 1) + ".scs";
        writeAfterNthLine(String.format("%s/%s", path, name), translatorToSCS.getMainIdtf());

    }

    public void writeAfterNthLine(String filename, String text) throws IOException{
        File file = new File(filename);
        String pointer = "rrel_key_sc_element:";
        File temp = File.createTempFile("temp-file-name", ".tmp");
        BufferedReader br = new BufferedReader(new FileReader( file ));
        PrintWriter pw =  new PrintWriter(new FileWriter( temp ));
        String line;
        while ((line = br.readLine()) != null) {
            pw.println(line);
            if (line.contains(pointer)) {
                pw.println("\t\t" + text + ';');
            }
        }
        br.close();
        pw.close();
        file.delete();
        temp.renameTo(file);
    }
}
