package de.famoushistory.EconomySystem.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    public static File file = new File("plugins/Simple-Economy", "config.yml");
    public static FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);

    public static File player = new File("plugins/Simple-Economy/data", "player.yml");
    public static FileConfiguration playerConfiguration = YamlConfiguration.loadConfiguration(player);

    public static File mysql = new File("plugins/Simple-Economy", "mysql.yml");
    public static FileConfiguration mysqlConfiguration = YamlConfiguration.loadConfiguration(mysql);

    public static File getPlayerFile() {
        return player;
    }

    public static File getMysqlFile() {
        return mysql;
    }

    public static File getFile() {
        return file;
    }

    public static FileConfiguration getPlayerConfiguration() {
        return playerConfiguration;
    }
    public static FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }
    public static FileConfiguration getMysqlConfiguration() {
        return mysqlConfiguration;
    }

    public static void reloadFile() {
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }
    public static void reloadMysql() {
        fileConfiguration = YamlConfiguration.loadConfiguration(mysql);
    }

    public static void reloadPlayerFile() {
        playerConfiguration = YamlConfiguration.loadConfiguration(player);
    }

    public static void savePlayerFile() {
        try {
            playerConfiguration.save(player);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveMysqlFile() {
        try {
            mysqlConfiguration.save(mysql);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveFile() {
        try {
            fileConfiguration.save(file);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
