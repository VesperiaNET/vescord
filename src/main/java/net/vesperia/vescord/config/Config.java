package net.vesperia.vescord.config;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

public class Config {
    private final Logger logger = Logger.getLogger("Config");
    private final File file;
    private JSONObject json;

    public Config(Path path, String name) {
        this.file = new File(path.toFile(), name);
    }

    public void load() throws IOException {
        
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                Files.write(file.toPath(), "{}".getBytes());
            } catch (Exception e) {
                this.logger.severe("Failed to create config file " + file.getName() + ": " + e.getMessage());
            }
        }
        String rawJson = Files.readString(file.toPath());
        this.json = new JSONObject(rawJson);
    }

    public void save() throws IOException {
        Files.writeString(file.toPath(), json.toString(4));
    }

    public void addDefault(String key, Object value) {
        this.json.put(key, value);
        try {
            this.save();
        } catch (IOException e) {
            this.logger.severe("Failed to save config file: " + file.getName());
        }
    }

    public JSONObject getJson() {
        return json;
    }

    public File getFile() {
        return file;
    }
}
