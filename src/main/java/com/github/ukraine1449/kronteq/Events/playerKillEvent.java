package com.github.ukraine1449.kronteq.Events;

import com.github.ukraine1449.kronteq.Kronteq;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class playerKillEvent implements Listener {
Kronteq plugin;

    public playerKillEvent(Kronteq plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerDeathEvent(PlayerDeathEvent event) throws Exception {
        if(event.getEntity().getKiller() != null){
            if(event.getEntity().getKiller() instanceof Player){
                if(event.getEntity().getWorld().getName().equals("Sumo2")){
                    if(plugin.sumo2.contains(event.getEntity())){
                        plugin.sumo2.remove(event.getEntity());
                        plugin.postOnKill(plugin.sumo2.get(0).toString(), event.getEntity().toString());
                        plugin.teleportBackToHub(event.getEntity(), plugin.sumo2.get(0));
                        Bukkit.broadcastMessage(event.getEntity() + " SEP " + plugin.sumo2.get(0));
                        plugin.sumo2.clear();
                        plugin.freeArenas.add("Sumo2");
                        plugin.playerTeleportToReady();
                    }
                }
            }
        }
    }
}