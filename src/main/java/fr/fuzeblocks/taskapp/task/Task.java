package fr.fuzeblocks.taskapp.task;

import com.google.gson.annotations.Expose;
import fr.fuzeblocks.taskapp.task.priority.TaskPriority;

import java.sql.Time;
import java.util.Objects;

public class Task {
    @Expose
    private String taskName;
    @Expose
    private String subTitle;
    @Expose
    private String content;
    @Expose
    private Time lastEdited;
    @Expose
    private Time created;
    @Expose
    private long charsInTask;
    @Expose
    private TaskPriority priority;

    public Task(String taskName, String subTitle, String content, Time lastEdited, Time created, TaskPriority priority) {
        this.taskName = taskName;
        this.subTitle = subTitle;
        this.content = content;
        this.lastEdited = lastEdited;
        this.created = created;
        this.charsInTask = content.length();
        this.priority = priority;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Time getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(Time lastEdited) {
        this.lastEdited = lastEdited;
    }

    public Time getCreated() {
        return created;
    }

    public void setCreated(Time created) {
        this.created = created;
    }

    public long getCharsInTask() {
        return charsInTask;
    }

    public void setCharsInTask(long charsInTask) {
        this.charsInTask = charsInTask;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Nom de la tache : " + taskName +
                ", Sous-titre : " + subTitle +
                 ", Edité le : " + lastEdited +
                ", Crée le : " + created +
                ", Nombre de character : " + charsInTask +
                ", Priorité : " + priority
                ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;
        return charsInTask == task.charsInTask && Objects.equals(taskName, task.taskName) && Objects.equals(subTitle, task.subTitle) && Objects.equals(content, task.content) && Objects.equals(lastEdited, task.lastEdited) && Objects.equals(created, task.created) && priority == task.priority;
    }
}
