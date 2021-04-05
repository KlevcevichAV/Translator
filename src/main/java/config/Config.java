package config;

public class Config {
    public static final String OSTIS_PATH = "/home/alex/IdeaProjects/bookApp-master";
    public static final String AUTHORS_PATH = OSTIS_PATH + "/kb/kb_books/subject_domain_of_books/section_subject_domain_of_books/section_authors_of_literature/section_russian_authors/section_russian_authors.scs";
    public static final String SILVER_AGE_PATH = OSTIS_PATH + "/kb/kb_books/subject_domain_of_lyric_poetry/section_lyric_poetry/section_silver_age_of_russian_poetry";
    public static final String GOLDEN_AGE_PATH = OSTIS_PATH + "/kb/kb_books/subject_domain_of_lyric_poetry/section_lyric_poetry/section_golden_age_of_russian_poetry";
    public static final String DEFAULT_PATH = System.getProperty("user.dir") + "/verses";
}

