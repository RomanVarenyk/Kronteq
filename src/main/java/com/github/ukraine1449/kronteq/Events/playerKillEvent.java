package com.github.ukraine1449.kronteq.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class playerKillEvent implements Listener {

    @EventHandler
    public void playerDeathEvent(PlayerDeathEvent event) throws Exception {

        if(event.getEntity().getKiller() != null){
            if(event.getEntity().getKiller() instanceof Player){

            }
        }

    }


    }
