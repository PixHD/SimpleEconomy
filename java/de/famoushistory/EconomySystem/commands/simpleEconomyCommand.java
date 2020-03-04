package de.famoushistory.EconomySystem.commands;

import de.famoushistory.EconomySystem.config.ConfigManager;
import de.famoushistory.EconomySystem.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class simpleEconomyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration config = ConfigManager.getFileConfiguration();
        if(sender instanceof Player) {
            Player player = (Player) sender;
                if(args.length == 0) {
                    if(player.hasPermission("simpleEconomy.basic")) {
                        player.sendMessage(Main.getPrefix() + "§eDer Main Command von §1Simple-Economy §efür mehr Infos mache §6/eco help§e!");
                    }else {
                        player.sendMessage(Main.getPrefix() + "§cDazu hast du §4keine §cRechte!");
                    }
                }else if(args.length == 1) {
                    if(args[0].equals("help")) {
                        if(player.hasPermission("simpleEconomy.help")) {
                            player.sendMessage("§e---------§6Hilfe 1/1§e---------");
                            player.sendMessage("§b/eco <set|add|remove|help> <player> <amount>");
                            player.sendMessage("§b/se <help|reload|setcurrency>");
                            player.sendMessage("§e---------§6Hilfe 1/1§e---------");
                        }else
                            player.sendMessage(Main.getPrefix() + "§cDazu hast du §4keine §cRechte!");
                    }else if(args[0].equals("reload")) {
                        if(player.hasPermission("simpleEconomy.reload")) {
                            ConfigManager.reloadFile();
                            ConfigManager.reloadPlayerFile();
                            player.sendMessage(Main.getPrefix() + " §aDas Plugin wurde neu geladen!");
                        }else
                            player.sendMessage(Main.getPrefix() + "§cDazu hast du §4keine §cRechte!");
                    }else
                        player.sendMessage(Main.getPrefix() + "§cBitte benutze §6/se help§c!");
                }else if(args.length == 2) {
                    if(args[0].equals("setcurrency")) {
                        if(player.hasPermission("simpleEconomy.setCurrency")) {
                            if(args[1].equals("$") || args[1].equals("€")) {
                                config.set("currency", args[1]);
                                player.sendMessage(Main.getPrefix() + " §aDie währung wurde auf §2" + args[1] + " §aumgestellt!");
                                ConfigManager.saveFile();
                            }else
                                player.sendMessage(Main.getPrefix() + "§cBitte benutze §6/se setcurrency <$|€>§c!");
                        }else
                            player.sendMessage(Main.getPrefix() + "§cDazu hast du §4keine §cRechte!");
                    }else
                        player.sendMessage(Main.getPrefix() + "§cBitte benutze §6/se setcurrency <$|€>§c!");
                }else
                    player.sendMessage(Main.getPrefix() + " §cBitte benutze §6/se help§c!");
        }else
            sender.sendMessage(Main.getPrefix() + " §cDieser Command ist nur für Spieler!");
        return false;
    }
}
