package fr.fuzeblocks.taskapp.task.serialization;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.fuzeblocks.taskapp.Main;
import fr.fuzeblocks.taskapp.task.Task;
import fr.fuzeblocks.taskapp.task.priority.TaskPriority;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class TaskDeserialization {
    private static final List<Task> filteredTaskList = new ArrayList<>();
    private static final List<Task> taskList = new ArrayList<>();
    private static final Set<Task> highTaskSet = new HashSet<>();
    private static final Set<Task> mediumTaskSet = new HashSet<>();
    private static final Set<Task> lowTaskSet = new HashSet<>();


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
        return getTasksWithoutContent(taskList);
    }
    public static List<Task> getTasksWithoutContent(List<Task> tasks) {
        List<Task> tasksWithoutContent = new ArrayList<>();
        for (Task task : tasks) {
            task.setContent("");
            tasksWithoutContent.add(task);
        }
        return tasksWithoutContent;
    }
    public static List<Task> getFilteredTaskList(List<Task> tasks) {
        for (Task task : tasks) {
            TaskPriority.Priority priority = task.getPriority();
            switch (priority) {
                case HIGH -> highTaskSet.add(task);
                case MEDIUM -> mediumTaskSet.add(task);
                case LOW -> lowTaskSet.add(task);
            }
            filteredTaskList.clear();
               filteredTaskList.addAll(highTaskSet.stream().toList());
              filteredTaskList.addAll(mediumTaskSet.stream().toList());
              filteredTaskList.addAll(lowTaskSet.stream().toList());
        }
        return filteredTaskList;
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
