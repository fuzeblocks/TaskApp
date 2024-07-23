package fr.fuzeblocks.taskapp.viewers.manager;

import fr.fuzeblocks.taskapp.Main;
import fr.fuzeblocks.taskapp.task.Task;
import fr.fuzeblocks.taskapp.viewers.controllers.EditTaskController;
import javafx.stage.Stage;


public class ViewerManager {
    private static Stage createTaskMenu;
    private static Stage editTaskMenu;
    private static Stage settingsMenu;

    public static Stage getCreateTaskMenu() {
        if (createTaskMenu == null) {
            createTaskMenu = Main.createStage("createTaskMenu.fxml", "Create New Task");
        }
        System.out.println("Called CreateTaskMenu");
        return createTaskMenu;
    }

    public static Stage getEditTaskMenu() {
        if (editTaskMenu == null) {
            editTaskMenu = Main.createStage("editTaskMenu.fxml", "Edit Task");
            configureEditTaskMenuEvents();
        }
        return editTaskMenu;
    }

    private static void configureEditTaskMenuEvents() {
        EditTaskController controller = (EditTaskController) editTaskMenu.getProperties().get("controller");

        editTaskMenu.setOnShown(event -> {
            if (controller != null && controller.task != null) {
                controller.setTask(controller.task);
            }
        });

        editTaskMenu.setOnHiding(event -> {
            if (controller != null) {
                controller.clearFields();
            }
        });
    }

    public static void openEditTaskMenu(Task task) {
        Stage editTaskMenu = getEditTaskMenu();
        EditTaskController controller = (EditTaskController) editTaskMenu.getProperties().get("controller");
        if (controller != null) {
            controller.setTask(task);
        }
        editTaskMenu.show();
    }

    public static Stage getSettingsMenu() {
        if (settingsMenu == null) {
            settingsMenu = Main.createStage("settingsController.fxml", "Edit settings");
        }
        System.out.println("Called settingsMenu");
        return settingsMenu;
    }
}
