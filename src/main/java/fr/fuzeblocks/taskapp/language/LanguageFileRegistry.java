package fr.fuzeblocks.taskapp.language;

import fr.fuzeblocks.taskapp.task.Parameters.Language;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class LanguageFileRegistry {
    private static Map<Language,File> languageFileMap = new HashMap();

    public static void setLanguageFile(Language language,File file) {
        languageFileMap.put(language,file);
    }

    public static File getFileFromLanguage(Language language) {
        return languageFileMap.get(language);
    }
}
