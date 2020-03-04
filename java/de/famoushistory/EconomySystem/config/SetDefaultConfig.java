package de.famoushistory.EconomySystem.config;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SetDefaultConfig {

    public static void setDefaultConfig() {
        try {
            String fullPath = ExportResource("/config.yml");
            moveConfigFile(fullPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setDefaultMysqlConfig(FileConfiguration config) {
        config.set("address", "localhost");
        config.set("username", "root");
        config.set("password", "password");
        config.set("port", "3306");
        config.set("database", "simpleeconomy");
        ConfigManager.saveMysqlFile();
    }

    static private String ExportResource(String resourceName) throws Exception {
        InputStream stream = null;
        OutputStream resStreamOut = null;
        String jarFolder;
        try {
            stream = SetDefaultConfig.class.getResourceAsStream(resourceName);
            if(stream == null) {
                throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }

            int readBytes;
            byte[] buffer = new byte[4096];
            jarFolder = new File(SetDefaultConfig.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');
            resStreamOut = new FileOutputStream(jarFolder + resourceName);
            System.out.println(jarFolder + resourceName);
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            stream.close();
            resStreamOut.close();
        }

        return jarFolder;
    }

    private static void moveConfigFile(String path) {
        Path temp = null;
        try {
            temp = Files.move
                    (Paths.get(path.replace('/', '\\') + "\\config.yml"),
                            Paths.get(path.replace('/', '\\') + "\\Simple-Economy\\config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(temp != null)
        {
            System.out.println("File renamed and moved successfully");
        }
        else
        {
            System.out.println("Failed to move the file");
        }
    }


}
