package com.github.ukraine1449.kronteq.Events;
import com.github.ukraine1449.kronteq.Kronteq;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class playerLeaveEvent implements Listener{
Kronteq plugin;

    public playerLeaveEvent(Kronteq plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerLeaveEvent(PlayerQuitEvent event){
        plugin.updateQueueListBoard();
        Player player = event.getPlayer();
        if(plugin.que.contains(player)){
            plugin.que.remove(player);
        }
    }

}
