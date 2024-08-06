package fr.fuzeblocks.taskapp;

import fr.fuzeblocks.taskapp.configuration.FileConfiguration;
import fr.fuzeblocks.taskapp.language.LanguageFileRegistry;
import fr.fuzeblocks.taskapp.language.LanguageManager;
import fr.fuzeblocks.taskapp.task.Parameters;
import fr.fuzeblocks.taskapp.task.Task;
import fr.fuzeblocks.taskapp.task.serialization.TaskDeserialization;
import fr.fuzeblocks.taskapp.viewers.TaskApplication;
import fr.fuzeblocks.taskapp.viewers.controllers.MainController;
import fr.fuzeblocks.taskapp.yaml.Yaml;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Optional;

public class Main {
    private static File savedTaskFile;
    private static Parameters parameters;
    private static LanguageManager languageManager;
    private static FileConfiguration fileConfiguration;
    public static void main(String[] args) throws IOException {
        loadFile();
        loadLanguage();
        TaskDeserialization.loadTasks();
        TaskApplication.main(args);
    }
    public static void loadLanguage() throws IOException {
        LanguageFileRegistry.setLanguageFile(Parameters.Language.FRENCH, inputStreamToFile(Main.class.getResourceAsStream("/lang/french.yml"),"french"));
        LanguageFileRegistry.setLanguageFile(Parameters.Language.ENGLISH,inputStreamToFile(Main.class.getResourceAsStream("/lang/english.yml"),"english"));
        parameters = new Parameters(Parameters.Language.valueOf(fileConfiguration.getString("Language")));
        languageManager = new LanguageManager(LanguageFileRegistry.getFileFromLanguage(parameters.getLanguage()));
    }
    public static void loadFile() {
        try {
            fileConfiguration = new FileConfiguration(inputStreamToFile(Main.class.getResourceAsStream("/config/config.yml"), "config"));
            savedTaskFile = new File("SavedTask.task").getCanonicalFile();
            if (!savedTaskFile.exists()) {
                System.out.println("File created : " + savedTaskFile.createNewFile() + " at " + savedTaskFile.getPath());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File getSavedTaskFile() {
        return savedTaskFile;
    }
    public static void showError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error : " + error);
        alert.setHeaderText(null);
        alert.setContentText(error);
        alert.showAndWait();
    }
    public static boolean deleteConfirmation(Task task) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(Main.getLanguageManager().getString("DeleteTaskMenu.DeleteTaskConfirmation").replace("%taskname%", task.getTaskName()));
        alert.setHeaderText(null);
        alert.setContentText(Main.getLanguageManager().getString("DeleteTaskMenu.DeleteTaskConfirmation").replace("%taskname%", task.getTaskName()));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get().equals(ButtonType.OK)) {
            return true;
        } else {
            MainController.updateTasks();
            alert.close();
            return false;
        }
    }
    public static Stage createStage(String path, String title) {
        FXMLLoader loader = new FXMLLoader(TaskApplication.class.getResource("/" + path));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        Scene scene = new Scene(pane,width,height);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.getProperties().put("controller", loader.getController());
        stage.setScene(scene);
        stage.show();

        return stage;
    }

    public static Parameters getParameters() {
        return parameters;
    }

    public static void setParameters(Parameters parameters) {
        Main.parameters = parameters;
    }

    public static LanguageManager getLanguageManager() {
        return languageManager;
    }

    public static FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }
    private static File inputStreamToFile(InputStream inputStream, String fileName) throws IOException {
        File tempFile = File.createTempFile(fileName, null);
        tempFile.deleteOnExit();

        try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        return tempFile;
    }
}
