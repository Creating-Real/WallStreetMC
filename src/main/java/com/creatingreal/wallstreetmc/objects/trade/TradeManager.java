package com.creatingreal.wallstreetmc.objects.trade;

import com.creatingreal.wallstreetmc.Core;
import com.creatingreal.wallstreetmc.objects.player.PlayerManager;
import com.creatingreal.wallstreetmc.objects.player.StockPlayer;
import com.creatingreal.wallstreetmc.objects.stock.Stock;
import com.creatingreal.wallstreetmc.objects.stock.StockManager;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
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

        if(pl == null){
            player.sendMessage("Player not found!");
            return;
        }

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

        BigDecimal totalPrice = new BigDecimal(price).multiply(BigDecimal.valueOf(Double.valueOf(quantity)));
        // round total to 2 decimal places
        totalPrice = totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
        // convert to double
        double totalPriceDouble = totalPrice.doubleValue();
        boolean active = true;

        Trade trade = new Trade(id, ticker, tradeType, quantity, price, date, time, totalPriceDouble, active);
        pl.getTrades().add(trade);
        pl.update();
        trades.add(trade);

        // Change stock price
        Stock stock = StockManager.getStockByTicker(ticker);
        stock.setVolume(stock.getVolume() + quantity);
        // DEPRECATED: Formula for new price if buying: Price = currentPrice + (currentPrice * (quantity / volume))
        // Formula for new price if buying: Price = currentPrice + (currentPrice * (quantity + volume / marketCap))

        // Formula for new price if selling: Price = currentPrice - (currentPrice * (quantity / volume))
        // Formula for new price if selling: Price = currentPrice - (currentPrice * (quantity + volume / marketCap))

        if(tradeType == TradeType.BUY){
//            double newPrice = stock.getPrice() + (stock.getPrice() * (quantity + stock.getVolume()) / stock.getMarketCap());
//            stock.setPrice(newPrice);

//            BigDecimal newPrice = new BigDecimal(stock.getPrice()).add(new BigDecimal(stock.getPrice()).multiply(new BigDecimal(quantity + stock.getVolume()).divide(new BigDecimal(stock.getMarketCap()), 2, BigDecimal.ROUND_HALF_UP)));
//            newPrice.setScale(2, RoundingMode.CEILING);
//            stock.setPrice(newPrice.doubleValue());

            DecimalFormat df = new DecimalFormat("#.##");
            double newPrice = Double.valueOf(df.format(stock.getPrice() + (stock.getPrice() * (quantity + stock.getVolume()) / stock.getMarketCap())));
            stock.setPrice(newPrice);

            // add to market cap
            stock.setMarketCap(stock.getMarketCap() + ((quantity * stock.getPrice()) / 2));

        } else {
//            double newPrice = stock.getPrice() - (stock.getPrice() * (quantity + stock.getVolume()) / stock.getMarketCap());
//            stock.setPrice(newPrice);

            DecimalFormat df = new DecimalFormat("#.##");
            double newPrice = Double.valueOf(df.format(stock.getPrice() - (stock.getPrice() * (quantity + stock.getVolume()) / stock.getMarketCap())));
            stock.setPrice(newPrice);

            // subtract from market cap
            // stock.setMarketCap(stock.getMarketCap() - ((quantity * stock.getPrice()) / stock.getMarketCap()));

        }

        // update stock
        Core.getInstance().getStockManager().saveStock(stock);

    }

}
