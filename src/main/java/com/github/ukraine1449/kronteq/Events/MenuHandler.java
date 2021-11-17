package com.github.ukraine1449.kronteq.Events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuHandler implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent event){
        if(event.getView().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Player stats")) {
            event.setCancelled(true);
        }else{
            return;
        }
    }

}
