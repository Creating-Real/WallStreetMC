package com.creatingreal.wallstreetmc.objects.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerManager implements Listener {

    private static List<StockPlayer> stockPlayers = new ArrayList<>();

    public static List<StockPlayer> getStockPlayers() {
        return stockPlayers;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        String uuid = player.getUniqueId().toString();

        if(getPlayer(player)==null){
            StockPlayer stockPlayer = new StockPlayer(uuid);

            if(stockPlayer.getUsername() != player.getName() && (stockPlayer.getUuid() == player.getUniqueId().toString())){
                // this means the player has changed their username
                stockPlayer.setUsername(player.getName());
                stockPlayer.update();
            }

            stockPlayers.add(stockPlayer);
        }

    }

    public static StockPlayer getPlayer(Player player){
        String uuid = player.getUniqueId().toString();
        for(StockPlayer stockPlayer : stockPlayers){
            if(stockPlayer.getUuid().equalsIgnoreCase(uuid)){
                return stockPlayer;
            }
        }
        return null;
    }

    public static StockPlayer getPlayer(UUID uuid){
        for(StockPlayer stockPlayer : stockPlayers){
            if(stockPlayer.getUuid().equalsIgnoreCase(uuid.toString())){
                return stockPlayer;
            }
        }
        return null;
    }

    public static StockPlayer getPlayer(String username){
        for(StockPlayer stockPlayer : stockPlayers){
            if(stockPlayer.getUsername().equalsIgnoreCase(username)){
                return stockPlayer;
            }
        }
        return null;
    }

}
