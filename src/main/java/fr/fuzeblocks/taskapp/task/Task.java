package fr.fuzeblocks.taskapp.task;

import com.google.gson.annotations.Expose;
import fr.fuzeblocks.taskapp.task.priority.TaskPriority;

import java.sql.Time;

public class Task {
    @Expose
    private String taskName;
    @Expose
    private String subTitle;
    @Expose
    private String content;
    @Expose
    private Time lastEdited = new Time(System.currentTimeMillis());
    @Expose
    private Time created;
    @Expose
    private long charsInTask;
    @Expose
    private TaskPriority.Priority priority;
    @Expose
    private Parameters parameters;

    public Task(String taskName, String subTitle, String content, Time lastEdited, Time created, TaskPriority.Priority priority, Parameters parameters) {
        this.taskName = taskName;
        this.subTitle = subTitle;
        this.content = content;
        this.lastEdited = lastEdited;
        this.created = created;
        this.charsInTask = content.length();
        this.priority = priority;
        this.parameters = parameters;
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

    public TaskPriority.Priority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority.Priority priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return switch (getParameters().getLanguage()) {
            case FRENCH -> {
                yield  "Nom de la tache : " + taskName +
                        ", Sous-titre : " + subTitle +
                        ", Edité le : " + lastEdited +
                        ", Crée le : " + created +
                        ", Nombre de character : " + charsInTask +
                        ", Priorité : " + TaskPriority.getPriorityTranslation(priority, Parameters.Language.FRENCH);

            }
            case ENGLISH -> {
                yield "Name of the task : " + taskName +
                        ", Sub-title : " + subTitle +
                        ", Last-edited : " + lastEdited +
                        ", Created-at : " + created +
                        ", Character : " + charsInTask +
                        ", Priority : " + TaskPriority.getPriorityTranslation(priority, Parameters.Language.ENGLISH);

            }
        };

    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

}
