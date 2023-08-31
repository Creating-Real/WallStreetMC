package com.creatingreal.wallstreetmc.objects.player;

import com.creatingreal.wallstreetmc.Core;
import com.creatingreal.wallstreetmc.objects.trade.Trade;
import com.creatingreal.wallstreetmc.objects.trade.TradeManager;
import com.creatingreal.wallstreetmc.objects.trade.TradeType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class StockPlayer {

    private String uuid;
    private String username;
    private String dateCreated;
    private double profitLoss;
    private double cash;
    private double netWorth;
    private List<Trade> trades;

    public StockPlayer(String uuid) {
        this.uuid = uuid;

        // check if data/uuid.yml exists
        // if it does, load the data
        // if it doesn't, create the file and set the dateCreated to the current date
        File newFile = new File(Core.getInstance().getDataFolder(), "data" + File.separator + uuid + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(newFile);
        if(newFile.exists()){
            // load data

            this.username = config.getString("username");
            this.dateCreated = config.getString("dateCreated");
            this.profitLoss = config.getDouble("profitLoss");
            this.cash = config.getDouble("cash");
            this.netWorth = config.getDouble("netWorth");

            // load trades
            List<Trade> trades = new ArrayList<>();
            for(String trade : config.getConfigurationSection("trades").getKeys(false)){
                /*
                    private String id;
                    private String ticker;
                    private TradeType type;
                    private int quantity;
                    private double price;
                    private String date;
                    private String time;
                    private double totalPrice;
                    private boolean active;
                 */
                String id = config.getString("trades." + trade + ".id");
                String ticker = config.getString("trades." + trade + ".ticker");
                TradeType type = TradeType.valueOf(config.getString("trades." + trade + ".type"));
                int quantity = config.getInt("trades." + trade + ".quantity");
                double price = config.getDouble("trades." + trade + ".price");
                String date = config.getString("trades." + trade + ".date");
                String time = config.getString("trades." + trade + ".time");
                double totalPrice = config.getDouble("trades." + trade + ".totalPrice");
                boolean active = config.getBoolean("trades." + trade + ".active");

                Trade newTrade = new Trade(id, ticker, type, quantity, price, date, time, totalPrice, active);
                trades.add(newTrade);
            }

            this.trades = trades;

        } else {
            try{

                // create file

                newFile.createNewFile();

                UUID uuid2 = UUID.fromString(uuid);
                config.set("uuid", uuid);
                config.set("username", Core.getInstance().getServer().getPlayer(uuid2).getName());

                String timeStamp = new SimpleDateFormat("MM/dd/yyyy hh:mm a").format(Calendar.getInstance().getTime());
                config.set("dateCreated", timeStamp);

                config.set("profitLoss", 0);
                config.set("cash", 0);
                config.set("netWorth", 0);
                config.createSection("trades");

                config.save(newFile);

            }catch(IOException e){
                e.printStackTrace();
            }
        }

    }

    public void update(){
        // update the data file
        File newFile = new File(Core.getInstance().getDataFolder(), "data" + File.separator + uuid + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(newFile);

        config.set("uuid", uuid);
        config.set("username", Core.getInstance().getServer().getPlayer(UUID.fromString(uuid)).getName());
        config.set("dateCreated", dateCreated);
        config.set("profitLoss", profitLoss);
        config.set("cash", cash);
        config.set("netWorth", netWorth);

        // save trades
        for(Trade trade : trades){
            config.set("trades." + trade.getId() + ".id", trade.getId());
            config.set("trades." + trade.getId() + ".ticker", trade.getTicker());
            config.set("trades." + trade.getId() + ".type", trade.getType().toString());
            config.set("trades." + trade.getId() + ".quantity", trade.getQuantity());
            config.set("trades." + trade.getId() + ".price", trade.getPrice());
            config.set("trades." + trade.getId() + ".date", trade.getDate());
            config.set("trades." + trade.getId() + ".time", trade.getTime());
            config.set("trades." + trade.getId() + ".totalPrice", trade.getTotalPrice());
            config.set("trades." + trade.getId() + ".active", trade.isActive());
        }

        try{
            config.save(newFile);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public double getProfitLoss() {
        return profitLoss;
    }

    public void setProfitLoss(double profitLoss) {
        this.profitLoss = profitLoss;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getNetWorth() {
        return netWorth;
    }

    public void setNetWorth(double netWorth) {
        this.netWorth = netWorth;
    }

    public List<Trade> getTrades() {
        return trades;
    }

    public void setTrades(List<Trade> trades) {
        this.trades = trades;
    }
}
