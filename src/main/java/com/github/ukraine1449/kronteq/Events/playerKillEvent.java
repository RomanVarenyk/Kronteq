package com.github.ukraine1449.kronteq.Events;

import com.github.ukraine1449.kronteq.Kronteq;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class playerKillEvent implements Listener {
Kronteq plugin;

    public playerKillEvent(Kronteq plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerDeathEvent(PlayerDeathEvent event) throws Exception {

        if(event.getEntity().getKiller() != null){
            if(event.getEntity().getKiller() instanceof Player){
                if(event.getEntity().getWorld().getName() == "SUMOWORLD"){
                    plugin.postOnKill(event.getEntity().getUniqueId().toString(), event.getEntity().getKiller().getUniqueId().toString());
                    //Set here to teleport both players to a set area where fighting preperations start.
                }
            }
        }
    }
}
