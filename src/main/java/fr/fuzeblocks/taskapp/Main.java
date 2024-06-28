package fr.fuzeblocks.taskapp;

import fr.fuzeblocks.taskapp.task.serialization.TaskDeserialization;
import fr.fuzeblocks.taskapp.viewers.TaskApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Main {
    private static File savedTaskFile;
    public static void main(String[] args) {
        loadFile();
        TaskDeserialization.loadTasks();
        TaskApplication.main(args);
    }
    private static void loadFile() {
        try {
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
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(error);
        alert.showAndWait();
    }
    public static Stage createStage(String path, String title) {
        FXMLLoader loader = new FXMLLoader(TaskApplication.class.getResource("/" + path));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(pane,1900,1000);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.getProperties().put("controller", loader.getController());
        stage.setScene(scene);
        stage.show();

        return stage;
    }
}
