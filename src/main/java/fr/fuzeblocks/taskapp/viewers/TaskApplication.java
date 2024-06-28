package fr.fuzeblocks.taskapp.viewers;

import fr.fuzeblocks.taskapp.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class TaskApplication extends Application {
    private static Stage mainStage;
    @Override
    public void start(Stage stage) throws IOException {
        //Main Screen
        FXMLLoader loader = new FXMLLoader(TaskApplication.class.getResource("/main.fxml"));
        AnchorPane pane = loader.load();
        Scene scene = new Scene(pane,1900,1000);
        mainStage = stage;
        stage.setTitle("TaskAPP");
        stage.setScene(scene);
        stage.show();



    }

    public static void main(String[] args) {
        new Main(); //Main class
        launch();
    }

    public static Stage getMainStage() {
        return mainStage;
    }

}