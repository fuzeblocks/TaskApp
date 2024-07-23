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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Time;

public class CreateTaskController {
    @FXML
    private TextField title; //Title
    @FXML
    protected TextField subTitle; //Sub title
    @FXML
    protected Button greenButton; //Event
    @FXML
    protected Button yellowButton; //Event
    @FXML
    protected Button coralButton; //Event

    protected Priority taskPriority = Priority.LOW; //Default TaskPriority

    @FXML
    protected void initialize() {
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
        if (title == null  || title.getCharacters().isEmpty()) {
            Main.showError("Vous devez definir un titre !");
            return;
        }
        Task task = new Task(String.valueOf(title.getCharacters()),String.valueOf(subTitle.getCharacters()),"",new Time(System.currentTimeMillis()),new Time(System.currentTimeMillis()),taskPriority,new Parameters(Parameters.Language.FRENCH));
        TaskDeserialization.addTask(task);
        MainController.updateTasks(task);
        try {
            TaskSerialization.saveTask(TaskDeserialization.getTasks());
        } catch (IOException e) {
            Main.showError("Impossible d'enregistrer la tache !");
        }
        title.clear();
        subTitle.clear();
        taskPriority = Priority.LOW;
        TaskApplication.getMainStage().show();
        ViewerManager.getCreateTaskMenu().hide();
    }
    private void cancelAndHideCreateMenu() {
        if (title != null && !title.getCharacters().isEmpty()) title.clear();
        if (subTitle != null && !subTitle.getCharacters().isEmpty()) subTitle.clear();
        taskPriority = Priority.LOW;
        TaskApplication.getMainStage().show();
        ViewerManager.getCreateTaskMenu().hide();

    }
}
