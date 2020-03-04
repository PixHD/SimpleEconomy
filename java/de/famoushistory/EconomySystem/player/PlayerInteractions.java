package de.famoushistory.EconomySystem.player;

import de.famoushistory.EconomySystem.ConfigOrMySQL.ConfigOrMySQL;
import de.famoushistory.EconomySystem.MySQLDatabase.MySQLController;
import de.famoushistory.EconomySystem.config.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import sun.security.krb5.Config;

import java.util.UUID;

public class PlayerInteractions {

    public static void registerPlayer(Player player) {
        if(ConfigOrMySQL.isMysqlEnabled()) {
            try {
                MySQLController.registerPlayer(player);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            FileConfiguration config = ConfigManager.getPlayerConfiguration();
            String uuid = player.getUniqueId().toString();
            config.set(uuid + ".isRegistered", true);
            config.set(uuid + ".balance", 0);
            ConfigManager.savePlayerFile();
        }
    }

    public static void depositPlayer(Player player, int amount) {
        if(ConfigOrMySQL.isMysqlEnabled()) {
            float currentBalance = 0;
            try {
                currentBalance = MySQLController.getPlayerBalance(player);
            } catch (Exception e) {
                e.printStackTrace();
            }
            int newBalance = (int) (currentBalance + amount);
            try {
                MySQLController.updatePlayerBalance(player, newBalance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            FileConfiguration config = ConfigManager.getPlayerConfiguration();
            String uuid = player.getUniqueId().toString();
            int currentBalance = config.getInt(uuid + ".balance");
            config.set(uuid + ".balance", amount + currentBalance);
            ConfigManager.savePlayerFile();
        }
    }

    public static void withdrawPlayer(Player player, int amount) {
        if(ConfigOrMySQL.isMysqlEnabled()) {
            float currentBalance = 0;
            try {
                currentBalance = MySQLController.getPlayerBalance(player);
            } catch (Exception e) {
                e.printStackTrace();
            }
            int newBalance = (int) (currentBalance - amount);
            try {
                MySQLController.updatePlayerBalance(player, newBalance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            FileConfiguration config = ConfigManager.getPlayerConfiguration();
            String uuid = player.getUniqueId().toString();
            int currentBalance = config.getInt(uuid + ".balance");
            config.set(uuid + ".balance", currentBalance-amount);
            ConfigManager.savePlayerFile();
        }
    }

    public static void setPlayerBalance(Player p, int amount) {
        if(ConfigOrMySQL.isMysqlEnabled()) {
            try {
                MySQLController.updatePlayerBalance(p, amount);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            FileConfiguration config = ConfigManager.getPlayerConfiguration();
            String uuid = p.getUniqueId().toString();
            config.set(uuid + ".balance", amount);
            ConfigManager.savePlayerFile();
        }
    }
}
