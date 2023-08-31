package com.creatingreal.wallstreetmc.objects.trade;

import com.creatingreal.wallstreetmc.Core;
import com.creatingreal.wallstreetmc.objects.player.PlayerManager;
import com.creatingreal.wallstreetmc.objects.player.StockPlayer;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TradeManager {

    private static List<Trade> trades = new ArrayList<>();

    public static List<Trade> getTrades() {
        return trades;
    }

    // Function: executeTrade
    public void executeTrade(Player player, String ticker, int quantity, double price, String type){

        StockPlayer pl = PlayerManager.getPlayer(player);

        if(!Core.getInstance().isMarketOpen){
            player.sendMessage(Core.getMessage("market-closed"));
            return;
        }

        /*
        String id, String ticker, TradeType type, int quantity, double price, String date, String time, double totalPrice, boolean active
         */

        // generate ID
        String id = "TRADE-" + trades.size() + "-" + player.getUniqueId().toString();
        TradeType tradeType = TradeType.valueOf(type);
        String timeStamp = new SimpleDateFormat("MM/dd/yyyy hh:mm a").format(Calendar.getInstance().getTime());
        String date = timeStamp.split(" ")[0];
        String time = timeStamp.split(" ")[1] + " " + timeStamp.split(" ")[2];
        double totalPrice = quantity * price;
        boolean active = true;

        Trade trade = new Trade(id, ticker, tradeType, quantity, price, date, time, totalPrice, active);
        pl.getTrades().add(trade);
        pl.update();
        trades.add(trade);

    }

}
