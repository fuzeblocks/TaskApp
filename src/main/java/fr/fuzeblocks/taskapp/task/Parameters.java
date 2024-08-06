package fr.fuzeblocks.taskapp.task;

import com.google.gson.annotations.Expose;


public class Parameters {
    @Expose
    private Language language = Language.ENGLISH;

    public Parameters(Language language) {
        this.language = language;
    }
    public Language getLanguage() {
        return language;
    }
    public void setLanguage(Language language) {
        this.language = language;
    }
    public static enum Language {
        FRENCH, ENGLISH
    }
    public static enum Color {
        BLACK,WHITE
    }
}
