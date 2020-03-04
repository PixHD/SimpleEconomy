package de.famoushistory.EconomySystem.MySQLDatabase;

import de.famoushistory.EconomySystem.config.ConfigManager;
import de.famoushistory.EconomySystem.main.Main;
import de.famoushistory.EconomySystem.player.PlayerInformation;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.sql.*;

public class MySQLController {
        public static void setupDatabase() throws Exception {
            createTable();
        }

        public static void registerPlayer(Player p) throws Exception {
            if(!PlayerInformation.playerIsRegistered(p)) {
                try {
                    Connection con = getConnection();
                    boolean isRegistered = true;
                    PreparedStatement posted = con.prepareStatement("INSERT INTO SimpleEconomy (UUID, BALANCE) VALUES ('" + p.getUniqueId().toString() + "', 0.0)");
                    posted.executeUpdate();
                    posted.close();
                    con.close();
                }catch(Exception e) {
                    System.out.println(e);
                }
            }
        }

        public static boolean playerIsRegistered(Player p) throws Exception {
            boolean user = false;
            try {
                Connection con = getConnection();
                PreparedStatement statement = con.prepareStatement("SELECT * FROM SimpleEconomy WHERE UUID= '" + p.getUniqueId().toString()+ "'");
                ResultSet result = statement.executeQuery();
                user = result.next();
                statement.close();
                result.close();
                con.close();
                return user;

            }catch (SQLException e) {
                System.out.println(e);
            }
            return user;
        }

        public static void updatePlayerBalance(Player p, int newBalance) throws Exception {
            try {
                Connection con = getConnection();
                PreparedStatement posted = con.prepareStatement("UPDATE SimpleEconomy SET BALANCE = '" + newBalance +"' WHERE UUID = '" + p.getUniqueId().toString()+ "'");
                posted.executeUpdate();
                posted.close();
                con.close();
            }catch (Exception e) {
                System.out.println(e);
            }
        }

        public static int getPlayerBalance(Player p) throws Exception {
            int balance = -1;
            try {
                Connection con = getConnection();
                PreparedStatement statement = con.prepareStatement("SELECT * FROM SimpleEconomy WHERE UUID= '" + p.getUniqueId().toString()+ "'");
                ResultSet result = statement.executeQuery();
                result.next();
                balance =  result.getInt("BALANCE");
                statement.close();
                result.close();
                con.close();
            }catch (Exception e) {
                System.out.println(e);
            }
            return balance;
        }

        private static void createTable() throws Exception {
            try {
                Connection con = getConnection();
                PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS SimpleEconomy(`UUID` CHAR(36) PRIMARY KEY, BALANCE BIGINT)");
                create.executeUpdate();
                create.close();
                con.close();
            }catch(Exception e) {
                System.out.println(e);
            }
            finally{
                System.out.println(Main.getPrefix() + "Die Tabelle wurde erstellt!");
            }
        }

        private static Connection getConnection() throws  Exception {
            FileConfiguration config = ConfigManager.getMysqlConfiguration();
            try {
                String driver = "com.mysql.jdbc.Driver";
                String url = "jdbc:mysql://" + config.get("address") + ":" + config.get("port") + "/" + config.get("database");
                String username = (String) config.get("username");
                String password = (String) config.get("password");
                Class.forName(driver);

                Connection con = DriverManager.getConnection(url, username, password);
                return con;
            }catch (Exception e) {
                System.out.println(e);
            }
            return null;
        }
}
