package fr.fuzeblocks.taskapp.task.priority;

public enum TaskPriority {
    LOW, MEDIUM, HIGH;

    public static class TaskConverter {
        public static String getFrenchPriority(TaskPriority taskPriority) {
            return switch (taskPriority) {
                case LOW -> "Faible";
                case MEDIUM -> "Moyen";
                case HIGH -> "Haut";
            };
        }
        public static String getEnglishPriority(TaskPriority taskPriority) {
            return switch (taskPriority) {
                case LOW -> "Low";
                case MEDIUM -> "Medium";
                case HIGH -> "High";
            };
        }
    }
}
