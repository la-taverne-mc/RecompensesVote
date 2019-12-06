package fr.neolithic.recompensesvote.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

import org.apache.commons.lang.Validate;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class FileManager {
    private JavaPlugin plugin;
    private String filename;

    private File file;
    private FileConfiguration fileConfiguration;

    public FileManager(@NotNull JavaPlugin plugin, String filename) {
        Validate.notNull(plugin, "plugin cannot be null");
        this.plugin = plugin;
        this.filename = filename;
        this.file = new File(plugin.getDataFolder(), filename);
    }

    public void reloadContent() {
        if (file == null) {
            file = new File(plugin.getDataFolder(), filename);
        }
        fileConfiguration = YamlConfiguration.loadConfiguration(file);

        InputStream defaultFileStream = plugin.getResource(filename);
        if (defaultFileStream != null) {
            YamlConfiguration defaultFile = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultFileStream));
            fileConfiguration.setDefaults(defaultFile);
        }
    }

    public FileConfiguration getContent() {
        if (fileConfiguration == null) {
            reloadContent();
        }
        return fileConfiguration;
    }

    public void save() {
        if (fileConfiguration != null && file != null) {
            try {
                getContent().save(file);
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Could not save to " + file, e);
            }
        }
    }

    public void saveDefault() {
        if (file == null) {
            file = new File(plugin.getDataFolder(), filename);
        }
        if (!file.exists()) {
            plugin.saveResource(filename, false);
        }
    }
}