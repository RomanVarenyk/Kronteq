package com.github.ukraine1449.kronteq;

import com.github.ukraine1449.kronteq.Commands.*;
import com.github.ukraine1449.kronteq.Events.MenuHandler;
import com.github.ukraine1449.kronteq.Events.playerJoinEvent;
import com.github.ukraine1449.kronteq.Events.playerKillEvent;
import com.github.ukraine1449.kronteq.Events.playerLeaveEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public final class Kronteq extends JavaPlugin {
public ArrayList<Player> que = new ArrayList<Player>();
public ArrayList<Player> sumo2 = new ArrayList<Player>();
public ArrayList<Player> sumo1 = new ArrayList<Player>();
public ArrayList<Player> sumo3 = new ArrayList<Player>();
public ArrayList<String> freeArenas = new ArrayList<String>();
public boolean isDuelFree = true;
public HashMap<Player, Player> duelList = new HashMap<Player, Player>();
public ArrayList<Player> cDuel = new ArrayList<Player>();
public ArrayList<Player> isInCurrentMatch = new ArrayList<Player>();
//8, 100, 162
    @Override
    public void onEnable() {
        freeArenas.add("Sumo2");
        freeArenas.add("Sumo1");
        freeArenas.add("Sumo3");
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        try {
            createTableUserStats();
        } catch (Exception e) {
            e.printStackTrace();
            consoleError("SQL user stats table creation");
        }
        getServer().getPluginManager().registerEvents(new MenuHandler(), this);
        getServer().getPluginManager().registerEvents(new playerJoinEvent(this), this);
        getServer().getPluginManager().registerEvents(new playerLeaveEvent(this), this);
        getCommand("duel").setExecutor(new Duel(this));
        getServer().getPluginManager().registerEvents(new playerKillEvent(this), this);
        getCommand("stats").setExecutor(new checkPlayerStats(this));
        getCommand("joinqueue").setExecutor(new joinQue(this));
        getCommand("getQueue").setExecutor(new getQue(this));
        getCommand("leavequeue").setExecutor(new leaveQueue(this));
    }
    public Connection getConnection() throws Exception{
        String ip = getConfig().getString("ip");
        String password = getConfig().getString("password");
        String username = getConfig().getString("username");
        String dbn = getConfig().getString("database name");
        try{
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://"+ ip + ":3306/" + dbn;
            System.out.println(url);
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected");
            return conn;
        }catch(Exception e){}
        return null;
    }
    public void createTableUserStats()throws Exception{
        try{
            Connection con = getConnection();
            PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS userStats(UUID varchar(255),wonGames int,lostGames int,kills int,deaths int, PRIMARY KEY (UUID))");
            create.executeUpdate();
            con.close();
        }catch(Exception e){}
    }
    public void consoleError(String Location){
        System.out.println("An error has occured with "+ Location+". Please look at the error code and attempt to troubleshoot, if not possible please contact the author. Discord: Ukraine#1449 Email: ukraine1449@gmail.com");
    }
    public void playerJoinQuery(String UUID){
        try{
            Connection con = getConnection();
            PreparedStatement posted = con.prepareStatement("INSERT INTO userStats(UUID, wonGames, lostGames, kills, deaths) VALUES ('"+UUID+"', 0, 0, 0, 0)ON DUPLICATE KEY UPDATE UUID='"+UUID+"'");
            posted.executeUpdate();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
            consoleError("player join first time addition to database");
        }
    }
    public ArrayList<Integer> selectCD(String UUID) throws Exception {
        System.out.println("ch1");
        ArrayList<Integer> retuns = new ArrayList<Integer>();
        Connection con = getConnection();
        System.out.println("ch2");
        PreparedStatement statement = con.prepareStatement("SELECT wonGames,lostGames,kills,deaths FROM userStats WHERE UUID='"+UUID+"'");
        ResultSet result = statement.executeQuery();
        System.out.println("ch3");
        while(result.next()){
            retuns.add(result.getInt("wonGames"));
            retuns.add(result.getInt("lostGames"));
            retuns.add(result.getInt("kills"));
            retuns.add(result.getInt("deaths"));
        }
        System.out.println("ch4");
        con.close();
        return retuns;
    }
    public void postOnKill(String UUIDK, String UUIDD) throws Exception {
     Connection con = getConnection();
     PreparedStatement updateWonGames = con.prepareStatement("UPDATE userStats SET wonGames=+1  WHERE UUID='"+UUIDK+"'");
         PreparedStatement updateLostGames = con.prepareStatement("UPDATE userStats SET lostGames=+1  WHERE UUID='"+UUIDD+"'");
         updateLostGames.executeUpdate();
         updateWonGames.executeUpdate();
        PreparedStatement updateDeaths = con.prepareStatement("UPDATE userStats SET kills=+1  WHERE UUID='"+UUIDK+"'");
        PreparedStatement updateKills = con.prepareStatement("UPDATE userStats SET deaths=+1 WHERE UUID='"+UUIDD+"'");
        updateKills.executeUpdate();
        updateDeaths.executeUpdate();
        con.close();
    }
    public void playerError(Player player, String Location){
        player.sendMessage(ChatColor.RED+"An error has occured with "+ Location+". Please look at the error code in the console, and attempt to troubleshoot, if not possible please contact the author. Discord: Ukraine#1449 Email: ukraine1449@gmail.com");
    }
    public void playerTeleportToReady(){
        if(que.size() >= 2){
            if(!freeArenas.isEmpty()){
                Location baseloc = que.get(0).getLocation();
                Bukkit.broadcastMessage(String.valueOf(baseloc));
                Location p1l = baseloc;
                Location p2l = baseloc;
                String arenaName = freeArenas.get(0);
                Player player1 = que.get(0);
                Player player2 = que.get(1);
                if(!isInCurrentMatch.contains(player1) && !isInCurrentMatch.contains(player2)){
                isInCurrentMatch.add(player1);
                isInCurrentMatch.add(player2);
                if(arenaName.equals("Sumo2")){
                    World world = getServer().getWorld("Sumo2");
                    sumo2.add(player1);
                    sumo2.add(player2);
                    que.remove(1);
                    que.remove(0);
                    p1l.setX(2);
                    p1l.setY(54);
                    p1l.setZ(-1);
                    p1l.setWorld(world);
                    p2l.setX(2);
                    p2l.setY(54);
                    p2l.setZ(-1);
                    p2l.setWorld(world);
                }else if(arenaName.equals("Sumo3")){
                    World world = getServer().getWorld("Sumo3");
                    sumo3.add(player1);
                    sumo3.add(player2);
                    que.remove(1);
                    que.remove(0);
                    p1l.setX(2);
                    p1l.setY(54);
                    p1l.setZ(-1);
                    p1l.setWorld(world);
                    p2l.setX(2);
                    p2l.setY(54);
                    p2l.setZ(-1);
                    p2l.setWorld(world);
                }else{
                    World world = getServer().getWorld("Sumo1");
                    sumo1.add(player1);
                    sumo1.add(player2);
                    que.remove(1);
                    que.remove(0);
                    p1l.setX(2);
                    p1l.setY(54);
                    p1l.setZ(-1);
                    p1l.setWorld(world);
                    p2l.setX(2);
                    p2l.setY(54);
                    p2l.setZ(-1);
                    p2l.setWorld(world);
                }}
                player1.teleport(p1l);
                player2.teleport(p2l);
                player1.sendMessage(ChatColor.RED + "Your match has started with " + player2.getDisplayName());
                player2.sendMessage(ChatColor.RED + "Your match has started with " + player1.getDisplayName());
            }
        }
    }
    public void teleportBackToHub(Player p1, Player p2){
        Location hub = getServer().getWorld("Practice").getSpawnLocation();
        p1.teleport(hub);
        p2.teleport(hub);
    }
    public void playerDuelStart(Player p1, Player p2, String args){
        if(isDuelFree){
            if(args.equals("a")){
                if(duelList.containsKey(p2)){
                if (duelList.get(p2).getPlayer().getUniqueId() == p1.getUniqueId()){
                    isDuelFree = false;
                    Location defloc = p1.getLocation();
                    defloc.setWorld(getServer().getWorld("duels"));
                    defloc.setX(2);
                    defloc.setY(54);
                    defloc.setZ(-1);
                    cDuel.add(p1);
                    cDuel.add(p2);
                    p1.teleport(defloc);
                    p2.teleport(defloc);
                    duelList.remove(p2);
                    isInCurrentMatch.add(p1);
                    isInCurrentMatch.add(p2);
                }}
            }else if(args.equals("d")){
                if(duelList.containsKey(p2)){
                    if (duelList.get(p2).getPlayer().getUniqueId() == p1.getUniqueId()){
                        p2.sendMessage(ChatColor.RED + "Your duel request has been denied.");
                        duelList.remove(p2);
                    }}
            }else if (args.equals("s")){
                duelList.putIfAbsent(p1, p2);
                p2.sendMessage(p1.getDisplayName() + " " + ChatColor.BLUE + "has invited you to duel. to accept do /duel " + p1.getDisplayName() + " a");
                p2.sendMessage(ChatColor.BLUE + "To deny the request to duel do /duel " + p1.getDisplayName() + " d");
            }
        }else{
            p1.sendMessage(ChatColor.RED + "The duel arena isnt free. Please try again later.");
        }
    }
}