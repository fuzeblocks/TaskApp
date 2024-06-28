package fr.fuzeblocks.taskapp.viewers.controllers;

import fr.fuzeblocks.taskapp.task.Task;
import fr.fuzeblocks.taskapp.task.priority.TaskPriority;
import fr.fuzeblocks.taskapp.task.serialization.TaskDeserialization;
import fr.fuzeblocks.taskapp.task.serialization.TaskSerialization;
import fr.fuzeblocks.taskapp.viewers.TaskApplication;
import fr.fuzeblocks.taskapp.viewers.manager.ViewerManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.Time;

public class EditTaskController {

    @FXML
    private TextField titleField;
    @FXML
    private TextField subTitleField;
    @FXML
    private TextArea contentField;
    @FXML
    private TextField priorityField;
    @FXML
    private Label lastEditedLabel;
    @FXML
    private Label createdLabel;

    public Task task;

    @FXML
    public void initialize() {
        // Ce code sera exécuté lorsque le contrôleur sera initialisé, mais avant que la tâche ne soit définie
    }

    public void setTask(Task task) {
        this.task = task;
        if (task != null) {
            titleField.setText(task.getTaskName());
            subTitleField.setText(task.getSubTitle());
            contentField.setText(task.getContent());
            priorityField.setText(task.getPriority().toString());
            // lastEditedLabel.setText(task.getLastEdited().toString());
            createdLabel.setText(task.getCreated().toString());
        }
    }

    public void clearFields() {
        titleField.clear();
        subTitleField.clear();
        contentField.clear();
        priorityField.clear();
        createdLabel.setText("");
        // lastEditedLabel.setText("");
    }

    @FXML
    protected void saveTask() {
        if (task != null) {
            task.setTaskName(titleField.getText());
            task.setSubTitle(subTitleField.getText());
            task.setPriority(TaskPriority.valueOf(priorityField.getText().toUpperCase()));
            task.setLastEdited(new Time(System.currentTimeMillis()));
            task.setContent(contentField.getText());

            TaskDeserialization.addTask(task);
            try {
                TaskSerialization.saveTask(TaskDeserialization.getTasks());
                MainController.updateTasks(task);
                clearFields();
                ViewerManager.getEditTaskMenu().hide();
                TaskApplication.getMainStage().show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    protected void cancelEdit() {
        clearFields();
        ViewerManager.getEditTaskMenu().hide();
        TaskApplication.getMainStage().show();
    }
}
