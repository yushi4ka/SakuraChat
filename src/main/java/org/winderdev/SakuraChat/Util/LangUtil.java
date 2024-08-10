package org.winterdev.SakuraChat.Util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.winterdev.SakuraChat.SakuraChat;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LangUtil {
    private static YamlConfiguration langConfig;
    private static String langFile;

    public static void init(Plugin plugin) {
        FileConfiguration config = SakuraChat.getPlugin().getConfig();
        langFile = config.getString("lang");
        InputStream inputStream = plugin.getResource("langs/" + langFile);
        langConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(inputStream));
    }

    public static String message(String key) {
        return langConfig.getString(key, "Message not found");
    }

    public static void reloadMessages(Plugin plugin) {
        File file = new File(plugin.getDataFolder(), "langs/" + langFile);
        langConfig = YamlConfiguration.loadConfiguration(file);
    }
}
