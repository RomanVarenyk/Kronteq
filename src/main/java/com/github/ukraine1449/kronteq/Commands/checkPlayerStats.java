package com.github.ukraine1449.kronteq.Commands;

import com.github.ukraine1449.kronteq.Kronteq;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
                ArrayList<Integer> returns = new ArrayList<Integer>();
                Player target = Bukkit.getPlayerExact(args[0]);
                try {
                    returns = plugin.selectCD(player.getUniqueId().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    plugin.playerError(player, "SQL request for user data. ");
                }
            }else{
                ArrayList<Integer> returns = new ArrayList<Integer>();
                try {
                    returns = plugin.selectCD(player.getUniqueId().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    plugin.playerError(player, "SQL request for user data. ");
                }
                if(!returns.isEmpty()){
                    double KDR;
                    if(returns.get(2) != 0 && returns.get(3) != 0){
                        KDR = returns.get(2)/returns.get(3);
                    }else {
                        KDR = 0;
                    }
                    ItemStack playerName = new ItemStack(Material.NAME_TAG);
                    ItemMeta PNM = playerName.getItemMeta();
                    PNM.setDisplayName(ChatColor.LIGHT_PURPLE + "Player name: " + player.getDisplayName());
                    playerName.setItemMeta(PNM);
                    gui.addItem(playerName);

                    ItemStack gamesWon = new ItemStack(Material.GOLD_INGOT);
                    ItemMeta GWM = gamesWon.getItemMeta();
                    GWM.setDisplayName(ChatColor.GOLD + "Games won: " + returns.get(0));
                    gamesWon.setItemMeta(GWM);
                    gui.addItem(gamesWon);

                    ItemStack lostGames = new ItemStack(Material.BLAZE_POWDER);
                    ItemMeta LGM = lostGames.getItemMeta();
                    LGM.setDisplayName(ChatColor.GOLD + "Games lost: " + returns.get(1));
                    lostGames.setItemMeta(LGM);
                    gui.addItem(lostGames);

                    ItemStack kills = new ItemStack(Material.DIAMOND_SWORD);
                    ItemMeta KM = kills.getItemMeta();
                    KM.setDisplayName(ChatColor.GOLD + "Kills: " + returns.get(2));
                    kills.setItemMeta(KM);
                    gui.addItem(kills);

                    ItemStack deaths = new ItemStack(Material.GOLD_INGOT);
                    ItemMeta DM = deaths.getItemMeta();
                    DM.setDisplayName(ChatColor.GOLD + "Deaths: " + returns.get(3));
                    deaths.setItemMeta(DM);
                    gui.addItem(deaths);
                    ItemStack KD = new ItemStack(Material.DIAMOND_AXE);
                    ItemMeta KDM = KD.getItemMeta();
                    KDM.setDisplayName(ChatColor.GOLD + "KD ratio: " + KDR);
                    KD.setItemMeta(KDM);
                    gui.addItem(KD);
                    player.openInventory(gui);
                    returns.clear();
            }}
        }
        return false;
    }

}
