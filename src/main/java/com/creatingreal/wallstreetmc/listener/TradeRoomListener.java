// TradeRoomListener.java - Responsible for listening for events in the Trade Room
// Created by: Joshua Lim
// Created on: 08/26/2023

// Packages
package com.creatingreal.wallstreetmc.listener;

// Imports
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

// Class
public class TradeRoomListener implements Listener {

    // Function if the player left-clicks
    @EventHandler
    public void onBlockLeftClick(PlayerInteractEvent event) {

        // Check if the block is a Diamond Block
        if (event.getClickedBlock().getType() == Material.DIAMOND_BLOCK) {

            // Display a message to the user
            event.getPlayer().sendMessage("You left-clicked a Diamond Block!");

        } else {

            // Display a message to the user
            event.getPlayer().sendMessage("This is not a Diamond Block!");

        }

    }

    // Function if the player right-clicks
    @EventHandler
    public void onBlockRightClick(PlayerInteractEvent event) {

        // Check if the block is a Diamond Block
        if (event.getClickedBlock().getType() == Material.DIAMOND_BLOCK) {

            // Display a message to the user
            event.getPlayer().sendMessage("You right-clicked a Diamond Block!");

        } else {

            // Display a message to the user
            event.getPlayer().sendMessage("This is not a Diamond Block!");

        }

    }

}
