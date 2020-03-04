package de.famoushistory.EconomySystem.main;

import de.famoushistory.EconomySystem.ConfigOrMySQL.ConfigOrMySQL;
import de.famoushistory.EconomySystem.commands.EcoCommand;
import de.famoushistory.EconomySystem.MySQLDatabase.MySQLController;
import de.famoushistory.EconomySystem.commands.MoneyCommand;
import de.famoushistory.EconomySystem.commands.simpleEconomyCommand;
import de.famoushistory.EconomySystem.config.ConfigManager;
import de.famoushistory.EconomySystem.config.SetDefaultConfig;
import de.famoushistory.EconomySystem.listener.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {

    private static ConsoleCommandSender console = Bukkit.getConsoleSender();

    @Override
    public void onEnable() {
        loadCommands();
        loadListener();
        loadStorage();
        console.sendMessage("Das Plugin 'Simple-Eco' wurde geladen!");
    }

    @Override
    public void onDisable() {
        console.sendMessage("Das Plugin 'Simple-Eco' wurde abgeschaltet!");
    }

    private void loadCommands() {
        getCommand("se").setExecutor(new simpleEconomyCommand());
        getCommand("eco").setExecutor(new EcoCommand());
        getCommand("money").setExecutor(new MoneyCommand());
    }

    private void loadListener() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), this);
    }

    private void loadConfig() {
        File file = ConfigManager.getFile();
        if(!file.exists()) {
            FileConfiguration config = ConfigManager.getFileConfiguration();
            SetDefaultConfig.setDefaultConfig();
            ConfigManager.savePlayerFile();
            console.sendMessage(getPrefix() +  "Die Config wurde erfolgreich erstellt!");
        }
    }
    public static String getPrefix() {
        String prefix = "§7[§9Simple-Eco§7] ";
        return prefix;
    }

    private void loadStorage() {
        if(ConfigOrMySQL.isMysqlEnabled()) {
                try {
                    MySQLController.setupDatabase();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }else {
            File file = ConfigManager.getMysqlFile();
            if(!file.exists()) {
                FileConfiguration config = ConfigManager.getMysqlConfiguration();
                SetDefaultConfig.setDefaultMysqlConfig(config);
                ConfigManager.saveMysqlFile();
                console.sendMessage(getPrefix() + "Das MySQL-File wurde erfolgreich erstellt!");
                console.sendMessage(getPrefix() + "Um Die Verbindung zur Datenbank herzustellen gebe bitte alle nötigen infos in die Datei ein");
                console.sendMessage(getPrefix() + ",starte den Server neu und setze in der Config den MySQL-Wert auf 'True'");
            }
            loadConfig();
        }
    }

}
