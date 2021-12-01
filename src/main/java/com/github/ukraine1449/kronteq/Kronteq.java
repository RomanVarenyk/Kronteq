package com.github.ukraine1449.kronteq;

import com.github.ukraine1449.kronteq.Commands.checkPlayerStats;
import com.github.ukraine1449.kronteq.Events.playerJoinEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public final class Kronteq extends JavaPlugin {

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
        getServer().getPluginManager().registerEvents(new playerJoinEvent(this), this);
        getCommand("stats").setExecutor(new checkPlayerStats(this));
    }

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
            //consoleError("player join first time addition to database");
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
        retuns.add(result.getInt("wonGames"));
        retuns.add(result.getInt("lostGames"));
        retuns.add(result.getInt("kills"));
        retuns.add(result.getInt("deaths"));
        System.out.println("ch4");
        con.close();
        return retuns;
    }
    public void playerError(Player player, String Location){
        player.sendMessage(ChatColor.RED+"An error has occured with "+ Location+". Please look at the error code in the console, and attempt to troubleshoot, if not possible please contact the author. Discord: Ukraine#1449 Email: ukraine1449@gmail.com");
    }
}
