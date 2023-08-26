// Core.java - Similar to server.js in node.js
// Created by: Mustafa Masody
// Created on: 08/26/2023

// Packages
package com.creatingreal.wallstreetmc;

// Imports
import com.creatingreal.wallstreetmc.util.FileManager;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

// Core Class
public final class Core extends JavaPlugin {

    // Create instance of Core
    private static Core instance;

    private FileManager fileManager;

    // Exports instance
    public static Core getInstance() {
        return instance;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public static YamlConfiguration getConfig(String file){
        return Core.getInstance().getFileManager().getConfig(file).get();
    }

    // Server Enables Plugin
    @Override
    public void onEnable() {

        // Set instance
        instance = this;
        fileManager = new FileManager(this);

        fileManager.getConfig("stocks.yml").copyDefaults(true).save();

        getConfig().options().copyDefaults(true);
        saveConfig();

        // Plugin startup logic
        getLogger().info("=====================================");
        getLogger().info("WallStreetMC Plugin is now enabled!");
        getLogger().info("=====================================");

    }

    // Server Disables Plugin
    @Override
    public void onDisable() {

        // Plugin shutdown logic
        getLogger().info("=====================================");
        getLogger().info("WallStreetMC Plugin is now disabled!");
        getLogger().info("=====================================");
    }


    // Basic Command
    // /testcommand -> Return back "Hello World!"
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Only want player to send command
        if ((sender instanceof Player)) {

            // Cast sender to Player object
            Player player = (Player) sender;

            // Check if command is /testcommand (not case sensitive)
            if (command.getName().equalsIgnoreCase("testcommand")) {

                // Execute command
                // Color Code Table: https://htmlcolorcodes.com/minecraft-color-codes/
                player.sendMessage("§a§lHello World!");

                return true;

            }

            return true;

        }

        return true;
    }

}
