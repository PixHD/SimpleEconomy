package de.famoushistory.EconomySystem.currency;

import de.famoushistory.EconomySystem.config.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;

public class CurrencyInformation {

    public static String getCurrency() {
        FileConfiguration config = ConfigManager.getFileConfiguration();
        String currency = (String) config.get("currency");
        return currency;
    }
}
