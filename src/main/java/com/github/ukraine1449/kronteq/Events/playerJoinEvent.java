package com.github.ukraine1449.kronteq.Events;

import com.github.ukraine1449.kronteq.Kronteq;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class playerJoinEvent implements Listener {
Kronteq plugin;

    public playerJoinEvent(Kronteq plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) throws Exception {

        Player player = event.getPlayer();
        plugin.playerJoinQuery(player.getUniqueId().toString());
        plugin.createQueueListBoard(player);
        plugin.updateQueueListBoard();
    }

}
