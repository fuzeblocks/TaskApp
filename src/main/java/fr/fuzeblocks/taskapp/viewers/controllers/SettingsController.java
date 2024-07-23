package fr.fuzeblocks.taskapp.viewers.controllers;

import fr.fuzeblocks.taskapp.Main;
import fr.fuzeblocks.taskapp.task.Parameters;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class SettingsController {
    @FXML
    private ChoiceBox languageBox;

    @FXML
    protected void initialize() {
        languageBox.setValue(Main.getParameters().getLanguage());
    }

    @FXML
    private void languageBox() {
        if (languageBox.getItems().isEmpty()) {
            languageBox.getItems().add(Parameters.Language.ENGLISH);
            languageBox.getItems().add(Parameters.Language.FRENCH);

            if (Main.getParameters().getLanguage().equals(languageBox.getValue())) {
                languageBox.getItems().remove(languageBox.getValue());
            }
            languageBox.hide();
            languageBox.show();
        }
    }

}
