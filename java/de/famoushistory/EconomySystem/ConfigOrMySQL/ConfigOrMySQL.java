package de.famoushistory.EconomySystem.ConfigOrMySQL;

import de.famoushistory.EconomySystem.config.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigOrMySQL {

    public static boolean isMysqlEnabled() {
        FileConfiguration config = ConfigManager.getFileConfiguration();
        if(config.getBoolean("mysql")) {
            return true;
        }else {
            return false;
        }
    }

    public static boolean isConfigEnabled() {
        FileConfiguration config = ConfigManager.getFileConfiguration();
        if(config.getBoolean("mysql")) {
            return false;
        }else {
            return true;
        }
    }
}
