// TradePortalGUI.java - Responsible for displaying the Trade Portal GUI
// Created by: Joshua Lim
// Created on: 08/26/2023

// Packages
package com.creatingreal.wallstreetmc.gui;

// Imports
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

// Class
public class TradePortalGUI implements Listener {

    // Declare Inventory variable
    private final Inventory inv;

    // Main constructor for the GUI
    public TradePortalGUI() {

        // Create a new inventory, with no owner (as this isn't a real inventory), a size of nine, called example
        inv = Bukkit.createInventory(null, 9, "Example");

        // Put the items into the inventory
        initializeItems();

    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {
        inv.addItem(createGuiItem(Material.DIAMOND_SWORD, "Example Sword", "§aFirst line of the lore", "§bSecond line of the lore"));
        inv.addItem(createGuiItem(Material.IRON_HELMET, "§bExample Helmet", "§aFirst line of the lore", "§bSecond line of the lore"));
    }

    // Nice little method to create a gui item with a custom name, and description
    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {

        // Create a new ItemStack, with the material we want
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        // Give the item the lore
        item.setItemMeta(meta);

        // Return the item
        return item;

    }

    // You can open the inventory with this
    public void openInventory(final HumanEntity ent) {
        ent.openInventory(inv);
    }

    // Check for clicks on items
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {

        // Check if the inventory is our inventory
        if (!e.getInventory().equals(inv)) return;

        // Check if the clicked slot is inside our inventory
        e.setCancelled(true);

        // Check if the item clicked is null
        final ItemStack clickedItem = e.getCurrentItem();

        // Verify current item is not null
        if (clickedItem == null || clickedItem.getType().isAir()) return;

        // Get the player that clicked the item
        final Player p = (Player) e.getWhoClicked();

        // Using slots click is a best option for your inventory click's
        p.sendMessage("You clicked at slot " + e.getRawSlot());

    }

    // Cancel dragging in our inventory
    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {

        // Check if the inventory is our inventory
        if (e.getInventory().equals(inv)) {
            e.setCancelled(true);
        }

    }

}