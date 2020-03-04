package de.famoushistory.EconomySystem.player;

import de.famoushistory.EconomySystem.ConfigOrMySQL.ConfigOrMySQL;
import de.famoushistory.EconomySystem.MySQLDatabase.MySQLController;
import de.famoushistory.EconomySystem.config.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;


public class PlayerInformation {

    public static boolean playerIsRegistered(Player player){
        boolean isRegistered = false;
        if(ConfigOrMySQL.isMysqlEnabled()) {
            try {
                isRegistered = MySQLController.playerIsRegistered(player);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            FileConfiguration playerConfig = ConfigManager.getPlayerConfiguration();
            String uuid = player.getUniqueId().toString();
            isRegistered = playerConfig.getBoolean(uuid + ".isRegistered");

        }
        return isRegistered;
    }

    public static int playerBalance(Player player) {
        int balance = -1;
        if(ConfigOrMySQL.isMysqlEnabled()) {
            try {
                balance = MySQLController.getPlayerBalance(player);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            FileConfiguration playerConfig = ConfigManager.getPlayerConfiguration();
            String uuid = player.getUniqueId().toString();
            balance = playerConfig.getInt(uuid + ".balance");
        }
        return balance;
    }

}
