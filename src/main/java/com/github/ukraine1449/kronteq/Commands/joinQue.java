package com.github.ukraine1449.kronteq.Commands;

import com.github.ukraine1449.kronteq.Kronteq;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class joinQue implements CommandExecutor {
Kronteq plugin;

    public joinQue(Kronteq plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;
            if(!plugin.que.contains(player)){
                if(!plugin.isInCurrentMatch.contains(player)){
                    plugin.que.add(player);
                    player.sendMessage(ChatColor.GREEN + "You have been added to the que. do /getQue to see the que");
                    plugin.updateQueueListBoard();
                    System.out.println(plugin.freeArenas);
                    if(!plugin.freeArenas.isEmpty()){
                        System.out.println(plugin.que);
                        if(plugin.que.size() >= 1)
                            plugin.playerTeleportToReady();
                    }
                }else{
                    player.sendMessage(ChatColor.RED + "You are currently in a match and are not able to join the queue");
                }
            }else{
                player.sendMessage(ChatColor.RED +"You are already in the que. do /getQue to see the que.");
            }
        }

        return false;
    }
}
