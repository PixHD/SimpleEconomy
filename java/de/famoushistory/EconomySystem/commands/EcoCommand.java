package de.famoushistory.EconomySystem.commands;

import de.famoushistory.EconomySystem.config.ConfigManager;
import de.famoushistory.EconomySystem.main.Main;
import de.famoushistory.EconomySystem.player.PlayerInformation;
import de.famoushistory.EconomySystem.player.PlayerInteractions;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class EcoCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration playerConfig = ConfigManager.getPlayerConfiguration();
        FileConfiguration config = ConfigManager.getFileConfiguration();
        String currency = (String) config.get("currency");
        if(sender instanceof Player) {
            Player player = (Player) sender;
                if(args.length == 0) {
                    if(player.hasPermission("simpleEconomy.eco.view.self")) {
                        int balance = PlayerInformation.playerBalance(player);
                        player.sendMessage(Main.getPrefix() + " §aDu hast §2 " + balance + currency + " §aauf deinem Konto!");
                    }else
                        noRights(player);
                }else if(args.length == 1) {
                    if(player.hasPermission("simpleEconomy.eco.view.others")) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if(target != null) {
                            if (PlayerInformation.playerIsRegistered(target) == true) {
                                int currentBalance = PlayerInformation.playerBalance(target);
                                player.sendMessage(Main.getPrefix() + "§aDer Spieler §7" + args[0] + "§a hat zurzeit §2" + currentBalance + currency + "§a auf dem Konto!");
                            } else
                                player.sendMessage(Main.getPrefix() + "§cDieser Spieler war noch nie auf dem Server!");
                        }else if(args[0].equals("help")) {
                            if(player.hasPermission("simpleEconomy.eco.help")) {
                                player.sendMessage(Main.getPrefix() + "§a Hilfe zum Befehl §2/eco§a!");
                                player.sendMessage("§a - /eco §m-- §a Zeigt deinen Kontostand an!");
                                player.sendMessage("§a - /eco <Name> §m-- §a Zeigt den Kontostand von <Name> an!");
                                player.sendMessage("§a - /eco set <Anzahl> §m-- §a Setzt deinen Kontostand auf <Anzahl>!");
                                player.sendMessage("§a - /eco set <Name> <Anzahl> §m-- §a Setzt den Kontostand von <Name> auf <Anzahl>!");
                                player.sendMessage("§a - /eco add <Anzahl> §m-- §a Fügt deinem Aktuellen Kontostand <Anzahl> hinzu!");
                                player.sendMessage("§a - /eco add <Name> <Anzahl> §m-- §a Fügt <Name> zu seinem Aktuellen Kontostand <Anzahl> hinzu!");
                                player.sendMessage("§a - /eco remove <Anzahl> §m-- §a Entzieht Dir <Anzahl> an Geld!");
                                player.sendMessage("§a - /eco remove <Name> <Anzahl> §m-- §a Entzieht <Name> <Anzahl> an Geld!");
                            }else
                                noRights(player);
                        }else {
                            if(args[0].equalsIgnoreCase("add")) {
                                player.sendMessage(Main.getPrefix() + "§cBitte benutze §6/eco add <Anzahl>§c!");
                            }else if(args[0].equalsIgnoreCase("set")) {
                                player.sendMessage(Main.getPrefix() + "§cBitte benutze §6/eco set <Anzahl>§c!");
                            }else if(args[0].equalsIgnoreCase("remove")) {
                                player.sendMessage(Main.getPrefix() + "§cBitte benutze §6/eco remove <Anzahl>§c!");
                            }else
                                dontOnline(player);
                        }

                    }else
                        noRights(player);
                }else if(args.length == 2) {
                    if(args[0].equals("add")) {
                        if(player.hasPermission("simpleEconomy.eco.add.self")) {
                            if (isInt(args[1])) {
                                int currentBalance = PlayerInformation.playerBalance(player);
                                int newBalance = currentBalance +  Integer.parseInt(args[1]);
                                PlayerInteractions.depositPlayer(player, Integer.parseInt(args[1]));
                                player.sendMessage(Main.getPrefix() + "§aDu hast nun §2" + newBalance + currency + "§a auf deinem Konto!");
                            }else
                                player.sendMessage(Main.getPrefix() + "§cBitte benutze §6/eco add <Anzahl>");
                        }else
                            player.sendMessage(Main.getPrefix() + "§cDafür hast du keine rechte!");
                    }else if(args[0].equals("set")) {
                        if(player.hasPermission("simpleEconomy.eco.set.self")) {
                            if(isInt(args[1])) {
                                PlayerInteractions.setPlayerBalance(player, Integer.parseInt(args[1]));
                                player.sendMessage(Main.getPrefix() + "§aDu hast nun §2" + args[1] + currency + "§a auf dem Konto!");
                            }else
                                player.sendMessage(Main.getPrefix() + "§cBitte benutze §6/eco set <Anzahl>");
                        }else
                            noRights(player);
                    }else if(args[0].equals("remove")) {
                        if(player.hasPermission("simpleEconomy.eco.add.self")) {
                            if (isInt(args[1])) {
                                String uuid = player.getUniqueId().toString();
                                int currentBalance = PlayerInformation.playerBalance(player);
                                int newBalance = currentBalance - Integer.parseInt(args[1]);
                                PlayerInteractions.withdrawPlayer(player, Integer.parseInt(args[1]));
                                player.sendMessage(Main.getPrefix() + "§aDu hast nun §2" + newBalance + currency + "§a auf dem Konto!");
                            }else
                                player.sendMessage(Main.getPrefix() + "§cBitte benutze §6/eco add <Anzahl>");
                        }else
                            noRights(player);
                    }else
                        player.sendMessage(Main.getPrefix() + "§cBitte benutze §6/eco help§c!");
                }else if(args.length == 3) {
                    if(args[0].equals("add")) {
                        if(player.hasPermission("simpleEconomy.eco.add.others")) {
                            if(isInt(args[2])) {
                                Player target = Bukkit.getPlayer(args[1]);
                                if(target != null) {
                                    String uuidTarget = target.getUniqueId().toString();
                                    if(PlayerInformation.playerIsRegistered(target)) {
                                        int currentBalance = PlayerInformation.playerBalance(target);
                                        int newBalance = currentBalance + Integer.parseInt(args[2]);
                                        PlayerInteractions.depositPlayer(target, Integer.parseInt(args[2]));
                                        player.sendMessage(Main.getPrefix() + "§aDer Spieler §7" + args[1] + "§a hat nun §2" + newBalance + currency + "§a auf den Konto!");
                                        target.sendMessage(Main.getPrefix() + "§aDu hast nun §2" + newBalance + currency + "§a auf den Konto!");
                                    }else
                                        player.sendMessage(Main.getPrefix() + "§cDieser Spieler war noch nie auf dem Server!");
                                }else {
                                    if(args[0].equalsIgnoreCase("add")) {
                                        player.sendMessage(Main.getPrefix() + "§cBitte benutze §6/eco add <Anzahl>§c!");
                                    }else if(args[0].equalsIgnoreCase("set")) {
                                        player.sendMessage(Main.getPrefix() + "§cBitte benutze §6/eco set <Anzahl>§c!");
                                    }else if(args[0].equalsIgnoreCase("remove")) {
                                        player.sendMessage(Main.getPrefix() + "§cBitte benutze §6/eco remove <Anzahl>§c!");
                                    }else
                                        dontOnline(player);
                                }
                            }else
                                player.sendMessage(Main.getPrefix() + "§cBitte benutze §6/eco add <Player> <Anzahl>§c!");
                        }else
                            noRights(player);
                    }else if(args[0].equals("set")) {
                        if(player.hasPermission("simpleEconomy.eco.set.others")) {
                            Player target = Bukkit.getPlayer(args[1]);
                            if(target != null) {
                                if(isInt(args[2])) {
                                    if (PlayerInformation.playerIsRegistered(target)) {
                                        PlayerInteractions.setPlayerBalance(target, Integer.parseInt(args[2]));
                                        player.sendMessage(Main.getPrefix() + "§aDer Spieler §7" + args[1] + "§a hat nun §2" + args[2] + currency + "§a auf dem Konto!");
                                        target.sendMessage(Main.getPrefix() + "§aDu hast nun §2" + args[2] + currency + "§a auf dem Konto!");
                                    } else
                                        player.sendMessage(Main.getPrefix() + "§cDieser Spieler war noch nie auf dem Server!");
                                }else
                                    player.sendMessage(Main.getPrefix() + "§cBitte benutze §6/eco set <Spieler> <Anzahl>§c!");
                            }else {
                                if(args[0].equalsIgnoreCase("add")) {
                                    player.sendMessage(Main.getPrefix() + "§cBitte benutze §6/eco add <Anzahl>§c!");
                                }else if(args[0].equalsIgnoreCase("set")) {
                                    player.sendMessage(Main.getPrefix() + "§cBitte benutze §6/eco set <Anzahl>§c!");
                                }else if(args[0].equalsIgnoreCase("remove")) {
                                    player.sendMessage(Main.getPrefix() + "§cBitte benutze §6/eco remove <Anzahl>§c!");
                                }else
                                    dontOnline(player);
                            }
                    }else
                        player.sendMessage("§cBitte benutze §6/eco help§c!");
                }else if(args[0].equals("remove")) {
                    if(player.hasPermission("simpleEconomy.eco.remove.others")) {
                        if(isInt(args[2])) {
                            Player target = Bukkit.getPlayer(args[1]);
                            if(target != null) {
                                if(PlayerInformation.playerIsRegistered(target)) {
                                    int currentBalance = PlayerInformation.playerBalance(target);
                                    int newBalance = currentBalance - Integer.parseInt(args[2]);
                                    PlayerInteractions.withdrawPlayer(target, Integer.parseInt(args[2]));
                                    player.sendMessage(Main.getPrefix() + "§aDer Spieler §7" + args[1] + "§a hat nun §2" + newBalance + currency + "§a auf den Konto!");
                                    target.sendMessage(Main.getPrefix() + "§aDu hast nun §2" + newBalance + currency + "§a auf den Konto!");
                                }else
                                    player.sendMessage(Main.getPrefix() + "§cDieser Spieler war noch nie auf dem Server!");
                            }else {
                                if(args[0].equalsIgnoreCase("add")) {
                                    player.sendMessage(Main.getPrefix() + "§cBitte benutze §6/eco add <Anzahl>§c!");
                                }else if(args[0].equalsIgnoreCase("set")) {
                                    player.sendMessage(Main.getPrefix() + "§cBitte benutze §6/eco set <Anzahl>§c!");
                                }else if(args[0].equalsIgnoreCase("remove")) {
                                    player.sendMessage(Main.getPrefix() + "§cBitte benutze §6/eco remove <Anzahl>§c!");
                                }else
                                    dontOnline(player);
                            }
                        }else
                            player.sendMessage(Main.getPrefix() + "§cBitte benutze §6/eco remove <Player> <Anzahl>§c!");
                    }else
                        player.sendMessage(Main.getPrefix() + "§cDafür hast du keine Rechte!");
                }else
                    player.sendMessage(Main.getPrefix() + "§cBitte benutze §6/eco help§c!");
            }else
                noRights(player);
        }else
            sender.sendMessage(Main.getPrefix() + "Dieser Command ist nur für Spieler!");
        return false;
    }

    private boolean isInt(String str) {
        try {
            Integer.parseInt(str);
        } catch (Throwable e) {
            return false;
        }
        return true;
    }

    private static void noRights(Player player) {
        player.sendMessage(Main.getPrefix() + " §cDazu hast du keine Rechte!");
    }

    private static void dontOnline(Player player) {
        player.sendMessage(Main.getPrefix() + "§cDieser Spieler ist momentan nicht online!");
    }
}
