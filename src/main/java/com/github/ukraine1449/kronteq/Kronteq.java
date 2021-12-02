package com.github.ukraine1449.kronteq;

import com.github.ukraine1449.kronteq.Commands.checkPlayerStats;
import com.github.ukraine1449.kronteq.Commands.getQue;
import com.github.ukraine1449.kronteq.Commands.joinQue;
import com.github.ukraine1449.kronteq.Events.MenuHandler;
import com.github.ukraine1449.kronteq.Events.playerJoinEvent;
import com.github.ukraine1449.kronteq.Events.playerKillEvent;
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

public final class Kronteq extends JavaPlugin {
public ArrayList<Player> que = new ArrayList<Player>();
public ArrayList<Player> sumo2 = new ArrayList<Player>();
public ArrayList<String> freeArenas = new ArrayList<String>();
    @Override
    public void onEnable() {
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
        getServer().getPluginManager().registerEvents(new playerKillEvent(this), this);
        getCommand("stats").setExecutor(new checkPlayerStats(this));
        getCommand("joinque").setExecutor(new joinQue(this));
        getCommand("getQue").setExecutor(new getQue(this));

    }
//TODO: make the playerKillEvent respond to burning in lava, remove the sumo2 clear on death and instead do it in the method, teleport both players to the HUB.
    @Override
    public void onDisable() {
        // Plugin shutdown logic
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
     PreparedStatement updateWonGames = con.prepareStatement("UPDATE userStats SET wonGames+=1  WHERE UUID="+UUIDK+"");
         PreparedStatement updateLostGames = con.prepareStatement("UPDATE userStats SET lostGames+=1  WHERE UUID="+UUIDD+"");
         updateLostGames.executeUpdate();
         updateWonGames.executeUpdate();
        PreparedStatement updateDeaths = con.prepareStatement("UPDATE userStats SET kills+=1  WHERE UUID="+UUIDK+"");
        PreparedStatement updateKills = con.prepareStatement("UPDATE userStats SET deaths+=1 WHERE UUID="+UUIDD+"");
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
                Location p1l = null;
                Location p2l = null;
                String arenaName = freeArenas.get(0);
                Player player1 = que.get(0);
                Player player2 = que.get(1);
                if(arenaName.equals("Sumo2")){
                    World world = getServer().getWorld("Sumo2");
                    sumo2.add(player1);
                    sumo2.add(player2);
                    que.remove(1);
                    que.remove(0);
                    p1l.setWorld(world);
                    p1l.setX(2);
                    p1l.setY(54);
                    p1l.setZ(3);
                    p2l.setWorld(world);
                    p2l.setX(2);
                    p2l.setY(54);
                    p2l.setZ(-6);
                }
                player1.teleport(p1l);
                player2.teleport(p2l);
                player1.sendMessage(ChatColor.RED + "Your match has started with " + player2.getDisplayName());
                player2.sendMessage(ChatColor.RED + "Your match has started with " + player1.getDisplayName());
            }
        }
    }
}
