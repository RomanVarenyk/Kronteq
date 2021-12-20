package com.github.ukraine1449.kronteq.Commands;

import com.github.ukraine1449.kronteq.Kronteq;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class leaveQueue implements CommandExecutor {
Kronteq plugin;

    public leaveQueue(Kronteq plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;
            plugin.que.remove(player);
            plugin.updateQueueListBoard();
            player.sendMessage(ChatColor.GREEN + "You have left the queue");
        }

        return false;
    }
}
