// Core.java - Similar to server.js in node.js
// Created by: Mustafa Masody
// Created on: 08/26/2023

// Packages
package com.creatingreal.wallstreetmc;

// Imports
import com.creatingreal.wallstreetmc.objects.player.PlayerManager;
import com.creatingreal.wallstreetmc.objects.player.StockPlayer;
import com.creatingreal.wallstreetmc.objects.stock.Stock;
import com.creatingreal.wallstreetmc.objects.stock.StockManager;
import com.creatingreal.wallstreetmc.objects.trade.TradeManager;
import com.creatingreal.wallstreetmc.objects.trade.TradeType;
import com.creatingreal.wallstreetmc.tasks.HoursTimer;
import com.creatingreal.wallstreetmc.util.FileManager;
import com.creatingreal.wallstreetmc.util.Settings;
import com.creatingreal.wallstreetmc.util.UtilTime;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.slf4j.helpers.Util;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;

// Core Class
public final class Core extends JavaPlugin {

    // Create instance of Core
    private static Core instance;

    public boolean isMarketOpen = false;

    private FileManager fileManager;
    private StockManager stockManager;
    private TradeManager tradeManager;

    // Exports instance
    public static Core getInstance() {
        return instance;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public StockManager getStockManager() {
        return stockManager;
    }

    public TradeManager getTradeManager() {
        return tradeManager;
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
        stockManager = new StockManager();
        tradeManager = new TradeManager();

        // check if "data" folder exists
        if(!(new File(getDataFolder(), "data").exists())){
            new File(getDataFolder(), "data").mkdir();
        }

        fileManager.getConfig("stocks.yml").copyDefaults(true).save();
        fileManager.getConfig("messages.yml").copyDefaults(true).save();

        getConfig().options().copyDefaults(true);
        saveConfig();

        Settings.loadSettings();

        // Implement Listeners
        getServer().getPluginManager().registerEvents(new PlayerManager(), this);
        //getServer().getPluginManager().registerEvents(new TradeRoomListener(), this);

        stockManager.loadStocks();

        if(Settings.MARKET_HOURS_MODE.equals("TIME_TICKS")){
            new HoursTimer().runTaskTimer(this, 0L, 1L);
        }else if(Settings.MARKET_HOURS_MODE.equals("ALWAYS_OPEN")){
            isMarketOpen = true;
        }

        if(Bukkit.getOnlinePlayers().size() > 0){
            getLogger().info("Detected " + Bukkit.getOnlinePlayers().size() + " players online. Updating their data...");
            for(Player player : Bukkit.getOnlinePlayers()){
                StockPlayer stockPlayer = new StockPlayer(player.getUniqueId().toString());
                PlayerManager.getStockPlayers().add(stockPlayer);
            }
        }

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

    public static String getMessage(String path){
        return getConfig("messages.yml").getString(path).replace("&", "§");
    }

    // Basic Command
    // /testcommand -> Return back "Hello World!"
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Only want player to send command
        if ((sender instanceof Player)) {

            // Cast sender to Player object
            Player player = (Player) sender;

            // Check if command is /testcommand (not case-sensitive)
            if (command.getName().equalsIgnoreCase("testcommand")) {

                if(args.length == 0){
                    player.sendMessage("§c§l[!] §cUsage: /testcommand <stock> <amount> <buy/sell>");
                    return true;
                }

                String stock = args[0];
                int amount = Integer.parseInt(args[1]);
                String type = args[2];

                BigDecimal total = new BigDecimal(StockManager.getStockByTicker(stock).getPrice()).multiply(new BigDecimal(amount));
                // round total to 2 decimal places
                total = total.setScale(2, BigDecimal.ROUND_HALF_UP);

                if(type.equalsIgnoreCase("buy")){
                    player.sendMessage("§6§l[!] §6Buying " + amount + " shares of " + stock + " for $" + StockManager.getStockByTicker(stock).getPrice() + " each.");
                    player.sendMessage("§6§l[!] §6Total: $" + total);
                    tradeManager.executeTrade(player, stock, amount, StockManager.getStockByTicker(stock).getPrice(), TradeType.BUY.name());
                    player.sendMessage("§6§l[!] §6Bought " + amount + " shares of " + stock + " for $" + StockManager.getStockByTicker(stock).getPrice() + " each.");
                }else if(type.equalsIgnoreCase("sell")){
                    player.sendMessage("§6§l[!] §6Selling " + amount + " shares of " + stock + " for $" + StockManager.getStockByTicker(stock).getPrice() + " each.");
                    player.sendMessage("§6§l[!] §6Total: $" + total);
                    tradeManager.executeTrade(player, stock, amount, StockManager.getStockByTicker(stock).getPrice(), TradeType.SELL.name());
                    player.sendMessage("§6§l[!] §6Sold " + amount + " shares of " + stock + " for $" + StockManager.getStockByTicker(stock).getPrice() + " each.");
                }

                return true;

            }

            return true;

        }

        return true;
    }

}
