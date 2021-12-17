package com.github.ukraine1449.kronteq.Commands;

import com.github.ukraine1449.kronteq.Kronteq;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class getQue implements CommandExecutor {
Kronteq plugin;

    public getQue(Kronteq plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;
            player.sendMessage(ChatColor.YELLOW + "The que is as follows:");
            for(int i = 0; i < plugin.que.size(); i++){
                int inQue = i+1;
                player.sendMessage(inQue + " " + plugin.que.get(i).getDisplayName());
            }
        }

        return false;
    }
}
