package fr.fuzeblocks.taskapp;

import fr.fuzeblocks.taskapp.task.Parameters;
import fr.fuzeblocks.taskapp.task.Task;
import fr.fuzeblocks.taskapp.task.serialization.TaskDeserialization;
import fr.fuzeblocks.taskapp.viewers.TaskApplication;
import fr.fuzeblocks.taskapp.viewers.controllers.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class Main {
    private static File savedTaskFile;
    private static Parameters parameters;
    public static void main(String[] args) {
        loadLanguage();
        loadFile();
        TaskDeserialization.loadTasks();
        TaskApplication.main(args);
    }
    private static void loadLanguage() {
        parameters = new Parameters(Parameters.Language.ENGLISH);
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
        alert.setTitle("Erreur : " + error);
        alert.setHeaderText(null);
        alert.setContentText(error);
        alert.showAndWait();
    }
    public static boolean deleteConfirmation(Task task) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Supprimer");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous vraiment supprimer : " + task.getTaskName() + " ?");
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
}
