package de.famoushistory.EconomySystem.listener;

import de.famoushistory.EconomySystem.ConfigOrMySQL.ConfigOrMySQL;
import de.famoushistory.EconomySystem.MySQLDatabase.MySQLController;
import de.famoushistory.EconomySystem.player.PlayerInformation;
import de.famoushistory.EconomySystem.player.PlayerInteractions;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void handlePlayerJoin(PlayerJoinEvent event) {
        if(ConfigOrMySQL.isMysqlEnabled()) {
            try {
                MySQLController.registerPlayer(event.getPlayer());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            if(!PlayerInformation.playerIsRegistered(event.getPlayer())) {
                PlayerInteractions.registerPlayer(event.getPlayer());
            }
        }
    }
}
