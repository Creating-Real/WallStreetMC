package com.creatingreal.wallstreetmc.objects.stock;

import com.creatingreal.wallstreetmc.Core;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;

public class StockManager {

    private static List<Stock> stocks = new ArrayList<>();

    public static List<Stock> getStocks() {
        return stocks;
    }

    public void loadStocks(){

        Bukkit.getLogger().info("Loading stocks...");

        YamlConfiguration c = Core.getConfig("stocks.yml");

        for(String key : c.getConfigurationSection("stocks").getKeys(false)){

            String name = c.getString("stocks." + key + ".name").replace("&", "§");
            List<String> description = new ArrayList<>();
            for(String descriptionKey : c.getStringList("stocks." + key + ".description")){
                description.add(descriptionKey.replace("&", "§"));
            }

            String ticker = c.getString("stocks." + key + ".ticker");
            double price = c.getDouble("stocks." + key + ".price");
            int marketCap = c.getInt("stocks." + key + ".marketCap");
            double volatility = c.getDouble("stocks." + key + ".volatility");
            int volume = c.getInt("stocks." + key + ".volume");
            double highOfTheDay = c.getDouble("stocks." + key + ".highOfTheDay");
            double lowOfTheDay = c.getDouble("stocks." + key + ".lowOfTheDay");
            double openPrice = c.getDouble("stocks." + key + ".openPrice");
            double closePrice = c.getDouble("stocks." + key + ".closePrice");
            double previousClose = c.getDouble("stocks." + key + ".previousClose");
            double allTimeHigh = c.getDouble("stocks." + key + ".allTimeHigh");
            double allTimeLow = c.getDouble("stocks." + key + ".allTimeLow");
            int allTimeVolume = c.getInt("stocks." + key + ".allTimeVolume");
            boolean halted = c.getBoolean("stocks." + key + ".halted");
            String haltReason = c.getString("stocks." + key + ".haltReason");
            int haltDuration = c.getInt("stocks." + key + ".haltDuration");
            String configIdentifier = c.getString("stocks." + key + ".configIdentifier");

            Stock stock = new Stock(name, description, ticker, price, marketCap, volatility, volume, highOfTheDay, lowOfTheDay, openPrice, closePrice, previousClose, allTimeHigh, allTimeLow, allTimeVolume, halted, haltReason, haltDuration, configIdentifier);
            stocks.add(stock);
        }

        Bukkit.getLogger().info("Loaded " + stocks.size() + " stocks!");

    }

    public void saveStock(Stock stock){
        YamlConfiguration c = Core.getConfig("stocks.yml");

        c.set("stocks." + stock.getConfigIdentifier() + ".name", stock.getName());
        c.set("stocks." + stock.getConfigIdentifier() + ".description", stock.getDescription());
        c.set("stocks." + stock.getConfigIdentifier() + ".ticker", stock.getTicker());
        c.set("stocks." + stock.getConfigIdentifier() + ".price", stock.getPrice());
        c.set("stocks." + stock.getConfigIdentifier() + ".marketCap", stock.getMarketCap());
        c.set("stocks." + stock.getConfigIdentifier() + ".volatility", stock.getVolatility());
        c.set("stocks." + stock.getConfigIdentifier() + ".volume", stock.getVolume());
        c.set("stocks." + stock.getConfigIdentifier() + ".highOfTheDay", stock.getHighOfTheDay());
        c.set("stocks." + stock.getConfigIdentifier() + ".lowOfTheDay", stock.getLowOfTheDay());
        c.set("stocks." + stock.getConfigIdentifier() + ".openPrice", stock.getOpenPrice());
        c.set("stocks." + stock.getConfigIdentifier() + ".closePrice", stock.getClosePrice());
        c.set("stocks." + stock.getConfigIdentifier() + ".previousClose", stock.getPreviousClose());
        c.set("stocks." + stock.getConfigIdentifier() + ".allTimeHigh", stock.getAllTimeHigh());
        c.set("stocks." + stock.getConfigIdentifier() + ".allTimeLow", stock.getAllTimeLow());
        c.set("stocks." + stock.getConfigIdentifier() + ".allTimeVolume", stock.getAllTimeVolume());
        c.set("stocks." + stock.getConfigIdentifier() + ".halted", stock.isHalted());
        c.set("stocks." + stock.getConfigIdentifier() + ".haltReason", stock.getHaltReason());
        c.set("stocks." + stock.getConfigIdentifier() + ".haltDuration", stock.getHaltDuration());

        Core.getInstance().getFileManager().getConfig("stocks.yml").save();
    }

    public static Stock getStock(String name){
        for(Stock stock : stocks){
            if(stock.getName().equalsIgnoreCase(name)){
                return stock;
            }
        }
        return null;
    }

    public static Stock getStockByTicker(String ticker){
        for(Stock stock : stocks){
            if(stock.getTicker().equalsIgnoreCase(ticker)){
                return stock;
            }
        }
        return null;
    }

}
