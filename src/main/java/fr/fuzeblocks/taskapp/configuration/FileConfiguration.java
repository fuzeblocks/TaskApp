package fr.fuzeblocks.taskapp.configuration;

import fr.fuzeblocks.taskapp.yaml.Yaml;

import java.io.File;

public class FileConfiguration extends Yaml {
    public FileConfiguration(File file) {
        super(file);
    }
}
