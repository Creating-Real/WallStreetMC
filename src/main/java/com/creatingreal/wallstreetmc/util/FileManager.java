package com.creatingreal.wallstreetmc.util;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.HashMap;

public class FileManager
{
    public static JavaPlugin plugin;
    public static HashMap<String, Config> configs;
    static {
        FileManager.configs = new HashMap<String, Config>();
    }

    public FileManager(final JavaPlugin pluginThis) {
        plugin = pluginThis;
    }

    public Config getConfig(final String name) {
        if (!FileManager.configs.containsKey(name)) {
            FileManager.configs.put(name, new Config(name));
        }
        return FileManager.configs.get(name);
    }

    public Config saveConfig(final String name) {
        return this.getConfig(name).save();
    }

    public Config reloadConfig(final String name) {
        return this.getConfig(name).reload();
    }

    public class Config
    {
        private String name;
        private File file;
        private YamlConfiguration config;

        public Config(final String name) {
            this.name = name;
        }

        public Config save() {
            if (this.config == null || this.file == null) {
                return this;
            }
            try {
                if (this.config.getConfigurationSection("").getKeys(true).size() != 0) {
                    this.config.save(this.file);
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
            return this;
        }

        public YamlConfiguration get() {
            if (this.config == null) {
                this.reload();
            }
            return this.config;
        }

        public Config saveDefaultConfig() {
            this.file = new File(FileManager.plugin.getDataFolder(), this.name);
            FileManager.plugin.saveResource(this.name, false);
            return this;
        }

        public Config reload() {
            if (this.file == null) {
                this.file = new File(FileManager.plugin.getDataFolder(), this.name);
            }
            this.config = YamlConfiguration.loadConfiguration(this.file);
            try {
                final Reader defConfigStream = new InputStreamReader(FileManager.plugin.getResource(this.name), "UTF8");
                if (defConfigStream != null) {
                    final YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                    this.config.setDefaults(defConfig);
                }
            }
            catch (UnsupportedEncodingException ex) {}
            catch (NullPointerException ex2) {}
            return this;
        }

        public Config copyDefaults(final boolean force) {
            this.get().options().copyDefaults(force);
            return this;
        }

        public Config set(final String key, final Object value) {
            this.get().set(key, value);
            return this;
        }

        public Object get(final String key) {
            return this.get().get(key);
        }
    }
}
