package fr.fuzeblocks.taskapp.task.serialization;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.fuzeblocks.taskapp.Main;
import fr.fuzeblocks.taskapp.task.Task;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TaskDeserialization {
    private static final List<Task> taskList = new ArrayList<>();

    public static void loadTasks() {
        Gson gson = new Gson();
        try (FileReader fileReader = new FileReader(Main.getSavedTaskFile())) {
            Type taskListType = new TypeToken<List<Task>>() {}.getType();
            List<Task> deserializedTasks = gson.fromJson(fileReader, taskListType);
            setTasks(deserializedTasks);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addTask(Task task) {
        if (!taskList.contains(task)) {
            taskList.add(task);
        } else {
            taskList.remove(task);
            taskList.add(task);
        }
    }

    public static List<Task> getTasks() {
        return taskList;
    }
    public static List<Task> getTasksWithoutContent() {
        List<Task> tasksWithoutContent = new ArrayList<>();
        for (Task task : taskList) {
            task.setContent("");
            tasksWithoutContent.add(task);
        }
        return tasksWithoutContent;
    }

    private static void setTasks(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            return;
        }
        for (Task task : tasks) {
            if (!taskList.contains(task)) {
                taskList.add(task);
            }
        }
    }
}
