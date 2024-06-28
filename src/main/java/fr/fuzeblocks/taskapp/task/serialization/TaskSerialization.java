package fr.fuzeblocks.taskapp.task.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.fuzeblocks.taskapp.Main;
import fr.fuzeblocks.taskapp.task.Task;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TaskSerialization {

    public static void saveTask(List<Task> task) throws IOException {
        System.out.println(task.isEmpty());
        try (FileWriter fileWriter = new FileWriter(Main.getSavedTaskFile())) {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
            gson.toJson(task, fileWriter);
            System.out.println("Writed !");
        }
    }
    public static void removeTask(Task task) throws IOException {
        TaskDeserialization.getTasks().remove(task);
        try (FileWriter fileWriter = new FileWriter(Main.getSavedTaskFile())) {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
            gson.toJson(TaskDeserialization.getTasks(), fileWriter);
        }
    }
}
