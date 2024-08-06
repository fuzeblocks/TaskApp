package fr.fuzeblocks.taskapp.viewers;

import fr.fuzeblocks.taskapp.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class TaskApplication extends Application {
    private static Stage mainStage;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(TaskApplication.class.getResource("/main.fxml"));
        mainStage = stage;
        AnchorPane pane = loader.load();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        Scene scene = new Scene(pane,width,height);
        stage.setTitle("TaskAPP");
        stage.setScene(scene);
        stage.show();



    }

    public static void main(String[] args) {
        launch();
    }

    public static Stage getMainStage() {
        return mainStage;
    }

}