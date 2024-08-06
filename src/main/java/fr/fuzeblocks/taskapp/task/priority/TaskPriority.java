package fr.fuzeblocks.taskapp.task.priority;

import fr.fuzeblocks.taskapp.Main;
import fr.fuzeblocks.taskapp.language.LanguageFileRegistry;
import fr.fuzeblocks.taskapp.language.LanguageManager;
import fr.fuzeblocks.taskapp.task.Parameters;
import fr.fuzeblocks.taskapp.task.Parameters.*;
import fr.fuzeblocks.taskapp.task.Task;

import java.util.HashMap;
import java.util.Map;

public class TaskPriority {
    private static final Map<Priority, Map<Parameters.Language, String>> priorityTranslations = new HashMap<>();
    private static final LanguageManager frenchManager = new LanguageManager(LanguageFileRegistry.getFileFromLanguage(Language.FRENCH));
    private static final LanguageManager englishManager = new LanguageManager(LanguageFileRegistry.getFileFromLanguage(Language.ENGLISH));
    static {
        String key = "Priority.";
        Map<Parameters.Language, String> lowTranslations = new HashMap<>();
        lowTranslations.put(Parameters.Language.FRENCH, frenchManager.getString(key+ "Low"));
        lowTranslations.put(Parameters.Language.ENGLISH, englishManager.getString(key + "Low"));
        priorityTranslations.put(Priority.LOW, lowTranslations);

        Map<Parameters.Language, String> mediumTranslations = new HashMap<>();
        mediumTranslations.put(Language.FRENCH, frenchManager.getString(key + "Medium"));
        mediumTranslations.put(Language.ENGLISH, englishManager.getString(key + "Medium"));
        priorityTranslations.put(Priority.MEDIUM, mediumTranslations);

        Map<Parameters.Language, String> highTranslations = new HashMap<>();
        highTranslations.put(Language.FRENCH, frenchManager.getString(key + "High"));
        highTranslations.put(Language.ENGLISH, englishManager.getString(key + "High"));
        priorityTranslations.put(Priority.HIGH, highTranslations);
    }

    public static String getPriorityTranslation(Priority taskPriority, Language language) {
        return priorityTranslations.get(taskPriority).get(language);
    }

    public static String getLanguagePriority(Task task) {
        return getPriorityTranslation(task.getPriority(), task.getParameters().getLanguage());
    }

    public static String getLanguagePriority(Task task, Priority priority) {
        return getPriorityTranslation(priority, task.getParameters().getLanguage());
    }

    public static Priority getPriority(String name, Language language) {
        return priorityTranslations.entrySet().stream()
                .filter(entry -> name.equalsIgnoreCase(entry.getValue().get(language)))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown priority: " + name));
    }
    public static Priority getPriority(String name) {
       return getPriority(name, Main.getParameters().getLanguage());
    }

    public enum Priority {
        LOW, MEDIUM, HIGH;
    }
}
