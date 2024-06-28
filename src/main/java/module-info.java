module fr.fuzeblocks.taskapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.sql;

    opens fr.fuzeblocks.taskapp to javafx.fxml;
    opens fr.fuzeblocks.taskapp.task to com.google.gson;
    opens fr.fuzeblocks.taskapp.task.priority to com.google.gson;

    exports fr.fuzeblocks.taskapp;
    exports fr.fuzeblocks.taskapp.viewers;
    opens fr.fuzeblocks.taskapp.viewers to javafx.fxml;
    exports fr.fuzeblocks.taskapp.viewers.controllers;
    opens fr.fuzeblocks.taskapp.viewers.controllers to javafx.fxml;
}
