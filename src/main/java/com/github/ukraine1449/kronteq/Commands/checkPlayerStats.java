package com.github.ukraine1449.kronteq.Commands;

import com.github.ukraine1449.kronteq.Kronteq;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class checkPlayerStats implements CommandExecutor {
Kronteq plugin;

    public checkPlayerStats(Kronteq plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;
            Inventory gui = Bukkit.createInventory(player, 9, ChatColor.GREEN + "Player stats");
            if(args.length > 0){
                Player target = Bukkit.getPlayerExact(args[0]);
                try {
                    ArrayList<Double> retuns = new ArrayList<Double>(plugin.selectCD(player.getUniqueId().toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                    plugin.playerError(player, "SQL request for user data. ");
                }
            }else{
                try {
                    ArrayList<Double> retuns = new ArrayList<Double>(plugin.selectCD(player.getUniqueId().toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                    plugin.playerError(player, "SQL request for user data. ");
                }
            }
        }
        return false;
    }

}
