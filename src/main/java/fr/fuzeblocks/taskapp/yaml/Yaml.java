package fr.fuzeblocks.taskapp.yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class Yaml {
    private File file;

    public Yaml(File file) {
        this.file = file;
    }

    public String getString(String key) {
        org.yaml.snakeyaml.Yaml yaml = new org.yaml.snakeyaml.Yaml();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Map<String, Object> yamlMap = yaml.load(fis);
        Object value = yamlMap;
        for (String part : key.split("\\.")) {
            if (value instanceof Map) {
                value = ((Map<?, ?>) value).get(part);
            } else {
                return null;
            }
        }
        return value != null ? value.toString() : null;
    }
}
