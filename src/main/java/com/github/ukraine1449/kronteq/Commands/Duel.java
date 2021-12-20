package com.github.ukraine1449.kronteq.Commands;

import com.github.ukraine1449.kronteq.Kronteq;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Duel implements CommandExecutor {
Kronteq plugin;

    public Duel(Kronteq plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;
            if(!plugin.isInCurrentMatch.contains(player)){
                try{
                    Player target = Bukkit.getPlayerExact(args[0]);
                    if(!plugin.isInCurrentMatch.contains(target)){
                        plugin.playerDuelStart(player, target, args[1]);
                    }else{
                        player.sendMessage(ChatColor.RED + "You are currently in a match and cannot duel someone.");
                    }
                }catch (Exception e){
                    plugin.playerError(player, "Player name");
                }
            }
        }

        return true;
    }
}
