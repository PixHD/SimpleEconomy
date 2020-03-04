package de.famoushistory.EconomySystem.commands;

import de.famoushistory.EconomySystem.config.ConfigManager;
import de.famoushistory.EconomySystem.main.Main;
import de.famoushistory.EconomySystem.player.PlayerInformation;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class MoneyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration playerConfig = ConfigManager.getPlayerConfiguration();
        FileConfiguration config = ConfigManager.getFileConfiguration();
        String currency = (String) config.get("currency");
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("simpleEconomy.eco.view.self")) {
                String uuid = player.getUniqueId().toString();
                int balance = PlayerInformation.playerBalance(player);
                player.sendMessage(Main.getPrefix() + " §aDu hast §2 " + balance + currency + " §aauf deinem Konto!");
            }else
                player.sendMessage(Main.getPrefix() + "§cDaszu hast du keine Rechte!");
        }else
            sender.sendMessage(Main.getPrefix() + "§cDazu hast du keine Rechte!");
        return false;
    }
}
