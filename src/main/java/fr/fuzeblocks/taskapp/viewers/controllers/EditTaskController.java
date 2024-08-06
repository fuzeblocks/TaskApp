package fr.fuzeblocks.taskapp.viewers.controllers;

import fr.fuzeblocks.taskapp.Main;
import fr.fuzeblocks.taskapp.task.Task;
import fr.fuzeblocks.taskapp.task.priority.TaskPriority;
import fr.fuzeblocks.taskapp.task.priority.TaskPriority.Priority;
import fr.fuzeblocks.taskapp.task.serialization.TaskDeserialization;
import fr.fuzeblocks.taskapp.task.serialization.TaskSerialization;
import fr.fuzeblocks.taskapp.viewers.TaskApplication;
import fr.fuzeblocks.taskapp.viewers.manager.ViewerManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.ResourceBundle;

public class EditTaskController implements Initializable {

    @FXML
    private TextField titleField;
    @FXML
    private TextField subTitleField;
    @FXML
    private TextArea contentField;
    @FXML
    private Label showLastEdited;
    @FXML
    private Label showCreatedLabel;
    @FXML
    private ChoiceBox priorityBox;
    @FXML
    private Label titleLabel;
    @FXML
    private Label subTitleLabel;
    @FXML
    private Label contentLabel;
    @FXML
    private Label priorityLabel;
    @FXML
    private Label lastEditedLabel;
    @FXML
    private Label createdLabel;
    @FXML
    private Button saveTaskButton;
    @FXML
    private Button cancelEditButton;

    public Task task;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String key = "EditTaskMenu.";
        titleLabel.setText(Main.getLanguageManager().getString(key + "Title"));
        subTitleLabel.setText(Main.getLanguageManager().getString(key + "SubTitle"));
        contentLabel.setText(Main.getLanguageManager().getString(key + "Content"));
        priorityLabel.setText(Main.getLanguageManager().getString(key + "Priority"));
        lastEditedLabel.setText(Main.getLanguageManager().getString(key + "LastEdited"));
        createdLabel.setText(Main.getLanguageManager().getString(key + "Created"));
        saveTaskButton.setText(Main.getLanguageManager().getString(key + "SaveTaskButton"));
        cancelEditButton.setText(Main.getLanguageManager().getString(key + "CancelEditButton"));
    }

    public void setTask(Task task) {
        this.task = task;
        if (task != null) {
            titleField.setText(task.getTaskName());
            subTitleField.setText(task.getSubTitle());
            contentField.setText(task.getContent());
            showLastEdited.setText(task.getLastEdited().toString());
            showCreatedLabel.setText(task.getCreated().toString());
            priorityBox.getSelectionModel().select(TaskPriority.getLanguagePriority(task));
        }
    }

    public void clearFields() {
        titleField.clear();
        subTitleField.clear();
        contentField.clear();
        priorityBox.getItems().clear();
        showCreatedLabel.setText("");
        showLastEdited.setText("");
    }

    @FXML
    private void saveTask() {
        if (task != null) {
            task.setCharsInTask(contentField.getLength());
            task.setTaskName(titleField.getText());
            task.setSubTitle(subTitleField.getText());
            task.setPriority(TaskPriority.getPriority((String) priorityBox.getValue(), task.getParameters().getLanguage()));
            task.setLastEdited(new Time(System.currentTimeMillis()));
            task.setContent(contentField.getText());
            TaskDeserialization.addTask(task);
            try {
                TaskSerialization.saveTask(TaskDeserialization.getTasks());
                MainController.updateTasks();
                clearFields();
                ViewerManager.getEditTaskMenu().hide();
                TaskApplication.getMainStage().show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void cancelEdit() {
        clearFields();
        ViewerManager.getEditTaskMenu().hide();
        TaskApplication.getMainStage().show();
    }
    @FXML
    private void priorityBoxClicked() {
        if (priorityBox.getItems().isEmpty()) {
            priorityBox.getItems().add(TaskPriority.getLanguagePriority(task,Priority.LOW));
            priorityBox.getItems().add(TaskPriority.getLanguagePriority(task,Priority.MEDIUM));
            priorityBox.getItems().add(TaskPriority.getLanguagePriority(task,Priority.HIGH));
            for (TaskPriority.Priority priority : TaskPriority.Priority.values()) {
                String taskPriorityTranslation = TaskPriority.getPriorityTranslation(priority,task.getParameters().getLanguage());
                if (taskPriorityTranslation.equalsIgnoreCase((String) priorityBox.getValue())) {
                    priorityBox.getItems().remove(taskPriorityTranslation);
                }
            }
            priorityBox.hide();
            priorityBox.show();
        }
    }
}
