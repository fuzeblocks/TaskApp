package fr.fuzeblocks.taskapp.viewers.controllers;

import fr.fuzeblocks.taskapp.task.Task;
import fr.fuzeblocks.taskapp.task.priority.TaskPriority;
import fr.fuzeblocks.taskapp.task.priority.TaskPriority.Priority;
import fr.fuzeblocks.taskapp.task.serialization.TaskDeserialization;
import fr.fuzeblocks.taskapp.task.serialization.TaskSerialization;
import fr.fuzeblocks.taskapp.viewers.TaskApplication;
import fr.fuzeblocks.taskapp.viewers.manager.ViewerManager;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
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
    private Label lastEditedLabel;
    @FXML
    private Label createdLabel;
    @FXML
    private ChoiceBox priorityBox;

    public Task task;


    public void setTask(Task task) {
        this.task = task;
        if (task != null) {
            titleField.setText(task.getTaskName());
            subTitleField.setText(task.getSubTitle());
            contentField.setText(task.getContent());
            lastEditedLabel.setText(task.getLastEdited().toString());
            createdLabel.setText(task.getCreated().toString());
            priorityBox.getSelectionModel().select(TaskPriority.getLanguagePriority(task));
        }
    }

    public void clearFields() {
        titleField.clear();
        subTitleField.clear();
        contentField.clear();
        priorityBox.getItems().clear();
        createdLabel.setText("");
        lastEditedLabel.setText("");
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
