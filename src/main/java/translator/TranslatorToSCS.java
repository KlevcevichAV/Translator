package translator;

import config.Config;
import entity.Verse;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class TranslatorToSCS {
    private StringBuilder file;

    public void translate(Verse verse) throws IOException {
        String SCAuthor = searchAuthor(verse.getAuthor());
        String mainIdtf = Translate.translate(verse.getTitle()).replaceAll(" ", "_").toLowerCase(Locale.ROOT);
        file = new StringBuilder();
        setVerseAttribute(mainIdtf, SCAuthor, verse);
        setTextVerse(mainIdtf, verse);
    }

    private void setVerseAttribute(String mainIdtf, String SCAuthor, Verse verse) throws IOException {
        setMainIdtf(mainIdtf, verse.getTitle());
        setVerse();
        setAuthor(SCAuthor);
        setLanguage();
        setFoot(verse.getFoot());
        setYear(verse.getDate());
    }

    private String searchAuthor(String author) throws IOException {
        String tempSCAuthor = String.format("person_%S", Translate.translate(author).replaceAll(" ", "_")).toLowerCase(Locale.ROOT);
        String file = readFile();
        int pointer = file.toLowerCase(Locale.ROOT).indexOf(tempSCAuthor);

        return file.substring(pointer, file.indexOf(";", pointer));
    }

    private String readFile() {
        File file = new File(Config.PATH_AUTHORS);
        StringBuilder result = new StringBuilder();
        try (FileReader fr = new FileReader(file)) {
            Scanner scan = new Scanner(fr);
            while (scan.hasNextLine()) {
                result.append(scan.nextLine());
            }
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return result.toString();
    }

    private void setMainIdtf(String title, String titleRu) {
        file.append(String.format("%s\n\t=> nrel_main_idtf:\n\t\t[\"%s\"]\n\t\t\t(* <- lang_ru;; *);\n\n", title, titleRu));
    }

    private void setVerse() {
        file.append("\t<- verse;\n");
    }

    private void setAuthor(String author) {
        file.append(String.format("\t=> nrel_author:\n\t%s;\n", author));

    }

    private void setLanguage() {
        file.append("\t=> nrel_original_language:\n\t\tlang_ru;\n");
    }

    private void setFoot(String foot) throws IOException {
        file.append(String.format("\t=> nrel_foot:\n\t\t%s;\n", Translate.translate(foot)));
    }

    private void setYear(int date) {
        file.append(String.format("\t=> nrel_publication_date:\n\t\tyear_%d;;\n\n", date));
    }

    private void setTextVerse(String mainIdtf, Verse verse) {
        file.append(String.format("%s => nrel_text:\n\t.%s", mainIdtf, mainIdtf));
        file.append(String.format("\n\t(*\n\t\t=> nrel_main_idtf:[Текст. \"%s\"] (* <-lang_ru;; *);;\n", verse.getTitle()));
        file.append(String.format("\t\t<= nrel_sc_text_translation:\n\t\t\t...\n\t\t\t(*\n\t\t\t\t -> \"file://content/%s.html\" (* <-lang_ru;; *);;", mainIdtf));
        file.append("\n\t\t\t*);;\n\n\t*);;");
    }

    public String getFile() {
        return file.toString();
    }
}
