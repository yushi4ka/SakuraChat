package org.winterdev.SakuraChat.Util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;
import org.winterdev.SakuraChat.SakuraChat;

public class EmojiUtil {
    private static Map<String, String> emojis = new HashMap<>();
    private static SakuraChat plugin;

    public static void init(SakuraChat plugin) {
        EmojiUtil.plugin = plugin;
        loadEmojis();
    }

    private static void loadEmojis() {
        File emojiFile = new File(plugin.getDataFolder(), "emoji.yml");
        if (!emojiFile.exists()) {
            plugin.saveResource("emoji.yml", false);
        }
        plugin.reloadConfig();
        YamlConfiguration emojiConfig = YamlConfiguration.loadConfiguration(emojiFile);
        for (String key : emojiConfig.getConfigurationSection("emoji").getKeys(false)) {
            String symbol = emojiConfig.getString("emoji." + key + ".symbol");
            String placeholder = emojiConfig.getString("emoji." + key + ".placeholder");
            emojis.put(placeholder, symbol);
        }
    }

    public static void reloadEmojis(SakuraChat plugin) {
        emojis.clear();
        loadEmojis();
    }

    public static String emoji(String message) {
        for (Map.Entry<String, String> entry : emojis.entrySet()) {
            message = message.replace(entry.getKey(), entry.getValue());
        }
        return message;
    }
}
