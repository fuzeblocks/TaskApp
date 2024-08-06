package fr.fuzeblocks.taskapp.viewers.controllers;

import fr.fuzeblocks.taskapp.Main;
import fr.fuzeblocks.taskapp.task.Parameters;
import fr.fuzeblocks.taskapp.task.Task;
import fr.fuzeblocks.taskapp.task.priority.TaskPriority.Priority;
import fr.fuzeblocks.taskapp.task.serialization.TaskDeserialization;
import fr.fuzeblocks.taskapp.task.serialization.TaskSerialization;
import fr.fuzeblocks.taskapp.viewers.TaskApplication;
import fr.fuzeblocks.taskapp.viewers.manager.ViewerManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.ResourceBundle;

public class CreateTaskController implements Initializable {
    @FXML
    private TextField titleField; //Title
    @FXML
    private Label titleLabel;
    @FXML
    protected TextField subTitleField; //Sub title
    @FXML
    private Label subTitleLabel;
    @FXML
    protected Button greenButton; //Event
    @FXML
    protected Button yellowButton; //Event
    @FXML
    protected Button coralButton; //Event
    @FXML
    private Label priorityLabel;
    @FXML
    private Button createButton;
    @FXML
    private Button cancelButton;

    protected Priority taskPriority = Priority.LOW; //Default TaskPriority
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Button Event
        greenButton.setOnAction(event -> {
            taskPriority = Priority.LOW;
        });

        yellowButton.setOnAction(event -> {
            taskPriority = Priority.MEDIUM;
        });

        coralButton.setOnAction(event -> {
            taskPriority = Priority.HIGH;
        });
        String key = "CreateTaskMenu.";
        titleLabel.setText(Main.getLanguageManager().getString(key + "Title"));
        subTitleLabel.setText(Main.getLanguageManager().getString(key + "SubTitle"));
        priorityLabel.setText(Main.getLanguageManager().getString(key + "Priority"));
        greenButton.setText(Main.getLanguageManager().getString("Priority.Low"));
        yellowButton.setText(Main.getLanguageManager().getString("Priority.Medium"));
        coralButton.setText(Main.getLanguageManager().getString("Priority.High"));
        createButton.setText(Main.getLanguageManager().getString(key + "CreateTaskButton"));
        cancelButton.setText(Main.getLanguageManager().getString(key + "CancelCreateTaskButton"));
    }

    @FXML
    protected void createTask() {
     saveAndCreateTask();
    }

    @FXML
    protected void cancelCreateTask() {
       cancelAndHideCreateMenu();
    }

    private void saveAndCreateTask() {
        if (titleField == null  || titleField.getCharacters().isEmpty()) {
            Main.showError(Main.getLanguageManager().getString("CreateTaskMenu.HaveNoTitle"));
            return;
        }
        Task task = new Task(String.valueOf(titleField.getCharacters()),String.valueOf(subTitleField.getCharacters()),"",new Time(System.currentTimeMillis()),new Time(System.currentTimeMillis()),taskPriority,Main.getParameters());
        TaskDeserialization.addTask(task);
        MainController.updateTasks();
        try {
            TaskSerialization.saveTask(TaskDeserialization.getTasks());
        } catch (IOException e) {
            Main.showError("Impossible d'enregistrer la tache !");
        }
        titleField.clear();
        subTitleField.clear();
        taskPriority = Priority.LOW;
        TaskApplication.getMainStage().show();
        ViewerManager.getCreateTaskMenu().hide();
    }
    private void cancelAndHideCreateMenu() {
        if (titleField != null && !titleField.getCharacters().isEmpty()) titleField.clear();
        if (subTitleField != null && !subTitleField.getCharacters().isEmpty()) subTitleField.clear();
        taskPriority = Priority.LOW;
        TaskApplication.getMainStage().show();
        ViewerManager.getCreateTaskMenu().hide();

    }
}
