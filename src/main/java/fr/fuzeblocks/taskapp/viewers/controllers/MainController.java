package fr.fuzeblocks.taskapp.viewers.controllers;

import fr.fuzeblocks.taskapp.Main;
import fr.fuzeblocks.taskapp.task.Task;
import fr.fuzeblocks.taskapp.task.serialization.TaskDeserialization;
import fr.fuzeblocks.taskapp.task.serialization.TaskSerialization;
import fr.fuzeblocks.taskapp.viewers.TaskApplication;
import fr.fuzeblocks.taskapp.viewers.manager.ViewerManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import java.io.IOException;



public class MainController {

    private static ObservableList<Task> notesList = FXCollections.observableArrayList(); //Observable Task (showedList)

    @FXML
    private ListView<Task> listView;


    @FXML
    protected void initialize() {
        //Show the Tasks
        listView.setItems(notesList);
        notesList.addAll(TaskDeserialization.getTasksWithoutContent(TaskDeserialization.getFilteredTaskList(TaskDeserialization.getTasks())));
    }

    @FXML
    protected void createTaskMenu() {
        ViewerManager.getCreateTaskMenu().show();
        TaskApplication.getMainStage().hide();
    }

    @FXML
    protected void editNote() {
        editTask();
    }

    @FXML
    protected void deleteNote() {
       removeNote();
    }
    @FXML
    protected void parameters() {
        ViewerManager.getSettingsMenu().show();
        TaskApplication.getMainStage().hide();
    }

    private int selectedTask() {
        System.out.println("Selected Task");
         return listView.getSelectionModel().getSelectedIndex();
    }
    private void editTask() {
        int selectedIndex = selectedTask();
        if (selectedIndex  > -1) {
              ViewerManager.openEditTaskMenu(notesList.get(selectedIndex));
            TaskApplication.getMainStage().hide();
            ViewerManager.getCreateTaskMenu().hide();
            System.out.println("Edit Task");
        } else {
            Main.showError("Veuillez choisir une tache a éditer !");
        }
    }

    private void removeNote() {
        int selectedIndex = selectedTask();
        if (selectedIndex != -1) {
            Task task = notesList.get(selectedIndex);
           try {
               if (Main.deleteConfirmation(task)) {
                   TaskSerialization.removeTask(task);
                   System.out.println("Remove Task");
                   notesList.remove(selectedIndex);
               }
            } catch (IOException e) {
                Main.showError("Impossible de supprimer la tache !");
            }
        }
    }

    public static void updateTasks() {
        notesList.clear();
        notesList.addAll(TaskDeserialization.getTasksWithoutContent(TaskDeserialization.getFilteredTaskList(TaskDeserialization.getTasks())));
    }
}
