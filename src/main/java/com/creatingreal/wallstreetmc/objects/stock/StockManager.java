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

            Stock stock = new Stock(name, description, ticker, price, marketCap, volatility, volume, highOfTheDay, lowOfTheDay, openPrice, closePrice, previousClose, allTimeHigh, allTimeLow, allTimeVolume, halted, haltReason, haltDuration);
            stocks.add(stock);
        }

        Bukkit.getLogger().info("Loaded " + stocks.size() + " stocks!");

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
