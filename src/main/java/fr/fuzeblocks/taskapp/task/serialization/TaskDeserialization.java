package fr.fuzeblocks.taskapp.task.serialization;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.fuzeblocks.taskapp.Main;
import fr.fuzeblocks.taskapp.task.Parameters;
import fr.fuzeblocks.taskapp.task.Task;
import fr.fuzeblocks.taskapp.task.priority.TaskPriority;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class TaskDeserialization {
    private static Set<Task> highTaskSet = new HashSet<>();
    private static Set<Task> mediumTaskSet = new HashSet<>();
    private static Set<Task> lowTaskSet = new HashSet<>();
    private static List<Task> filteredTaskList = new ArrayList<>();
    private static final List<Task> taskList = new ArrayList<>();


    public static void loadTasks() {
        Gson gson = new Gson();
        try (FileReader fileReader = new FileReader(Main.getSavedTaskFile())) {
            Type taskListType = new TypeToken<List<Task>>() {}.getType();
            List<Task> deserializedTasks = gson.fromJson(fileReader, taskListType);
            List<Task> verifiedTasks = checkTasks(deserializedTasks);
            setTasks(verifiedTasks);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean isInSelectedLanguage(Task task, Parameters.Language language) {
        return task.getParameters().getLanguage().equals(language);
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
        highTaskSet.clear();
        mediumTaskSet.clear();
        lowTaskSet.clear();
        filteredTaskList.clear();
        for (Task task : tasks) {
            TaskPriority.Priority priority = task.getPriority();
            switch (priority) {
                case HIGH:
                    highTaskSet.add(task);
                    break;
                case MEDIUM:
                    mediumTaskSet.add(task);
                    break;
                case LOW:
                    lowTaskSet.add(task);
                    break;
            }
        }
        if (!highTaskSet.isEmpty()) {
            setTasksInFilterList(highTaskSet);
        }
        if (!mediumTaskSet.isEmpty()) {
            setTasksInFilterList(mediumTaskSet);
        }
        if (!lowTaskSet.isEmpty()) {
            setTasksInFilterList(lowTaskSet);
        }
        return new ArrayList<>(filteredTaskList);
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
    private static void setTasksInFilterList(Set<Task> taskSet) {
        for (Task task : taskSet) {
            filteredTaskList.add(task);
        }
    }
    private static List<Task> checkTasks(List<Task> tasks) {
        List<Task> correctedTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (!isInSelectedLanguage(task,Main.getParameters().getLanguage())) {
                Task correctedTask = task.clone(task);
                correctedTask.getParameters().setLanguage(Main.getParameters().getLanguage());
                correctedTasks.add(correctedTask);
            } else {
                correctedTasks.add(task);
            }
        }
        try {
            TaskSerialization.saveTask(correctedTasks);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return correctedTasks;
    }
}
