package com.github.ukraine1449.kronteq.Events;

import com.github.ukraine1449.kronteq.Kronteq;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
        switch (event.getEntity().getWorld().getName()) {
            case "Sumo2":
                if (plugin.sumo2.contains(event.getEntity().getPlayer())) {
                    plugin.sumo2.remove(event.getEntity().getPlayer());
                    plugin.postOnKill(plugin.sumo2.get(0).toString(), event.getEntity().getPlayer().toString());
                    Bukkit.broadcastMessage(ChatColor.RED + "Follwoing is a statement about players in list. THIS IS FOR DEBUGING. ");
                    Bukkit.broadcastMessage(event.getEntity().getPlayer() + " SEP " + plugin.sumo2.get(0));
                    plugin.teleportBackToHub(event.getEntity().getPlayer(), plugin.sumo2.get(0));
                    plugin.sumo2.clear();
                    plugin.freeArenas.add("Sumo2");
                    plugin.playerTeleportToReady();
                }
                break;
            case "Sumo3":
                if (plugin.sumo3.contains(event.getEntity().getPlayer())) {
                    plugin.sumo3.remove(event.getEntity().getPlayer());
                    plugin.postOnKill(plugin.sumo3.get(0).toString(), event.getEntity().getPlayer().toString());
                    Bukkit.broadcastMessage(ChatColor.RED + "Follwoing is a statement about players in list. THIS IS FOR DEBUGING. ");
                    Bukkit.broadcastMessage(event.getEntity().getPlayer() + " SEP " + plugin.sumo3.get(0));
                    plugin.teleportBackToHub(event.getEntity().getPlayer(), plugin.sumo3.get(0));
                    plugin.sumo3.clear();
                    plugin.freeArenas.add("Sumo3");
                    plugin.playerTeleportToReady();
                }
                break;
            case "Sumo1":
                if (plugin.sumo1.contains(event.getEntity().getPlayer())) {
                    plugin.sumo1.remove(event.getEntity().getPlayer());
                    plugin.postOnKill(plugin.sumo1.get(0).toString(), event.getEntity().getPlayer().toString());
                    Bukkit.broadcastMessage(ChatColor.RED + "Follwoing is a statement about players in list. THIS IS FOR DEBUGING. ");
                    Bukkit.broadcastMessage(event.getEntity().getPlayer() + " SEP " + plugin.sumo1.get(0));
                    plugin.teleportBackToHub(event.getEntity().getPlayer(), plugin.sumo1.get(0));
                    plugin.sumo1.clear();
                    plugin.freeArenas.add("Sumo1");
                    plugin.playerTeleportToReady();
                }
                break;
            case "duels":
                plugin.cDuel.remove(event.getEntity().getPlayer());
                plugin.isDuelFree = true;
                plugin.postOnKill(plugin.cDuel.get(0).getUniqueId().toString(), event.getEntity().getPlayer().getUniqueId().toString());
                plugin.cDuel.clear();

        }
            }
        }