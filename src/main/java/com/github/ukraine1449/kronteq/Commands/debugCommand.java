package com.github.ukraine1449.kronteq.Commands;

import com.github.ukraine1449.kronteq.Kronteq;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class debugCommand implements CommandExecutor {
Kronteq plugin;

    public debugCommand(Kronteq plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;
            player.sendMessage("Que: " + plugin.que);
            player.sendMessage("Sumo1: " + plugin.sumo1);
            player.sendMessage("Sumo2: " + plugin.sumo2);
            player.sendMessage("Sumo3: " + plugin.sumo3);
            player.sendMessage("Free arenas: " + plugin.freeArenas);
            player.sendMessage("isDuelFree: " + plugin.isDuelFree);
            player.sendMessage("duelList: " + plugin.duelList);
            player.sendMessage("cDuel: " + plugin.cDuel);
        }else{
            System.out.println("Que: " + plugin.que);
            System.out.println("Sumo1: " + plugin.sumo1);
            System.out.println("Sumo2: " + plugin.sumo2);
            System.out.println("Sumo3: " + plugin.sumo3);
            System.out.println("Free arenas: " + plugin.freeArenas);
            System.out.println("isDuelFree: " + plugin.isDuelFree);
            System.out.println("duelList: " + plugin.duelList);
            System.out.println("cDuel: " + plugin.cDuel);
        }
        return true;
    }
}
