package org.winterdev.SakuraChat;

import java.io.InputStream;
import java.io.InputStreamReader;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.winterdev.SakuraChat.Commands.SChat;
import org.winterdev.SakuraChat.Listeners.*;
import org.winterdev.SakuraChat.Util.EmojiUtil;
import org.winterdev.SakuraChat.Util.LangUtil;

public final class SakuraChat extends JavaPlugin {

    private static SakuraChat plugin;
    private YamlConfiguration emojiConfig;
    private YamlConfiguration messagesRUConfig;
    private YamlConfiguration messagesENConfig;

    public SakuraChat() {
    }

    public void onEnable() {

        plugin = this;

        this.getConfig().options().copyDefaults();
        this.saveDefaultConfig();

        InputStream inputStream = this.getResource("emoji.yml");
        this.emojiConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(inputStream));
        saveEmojiConfig();
        inputStream = this.getResource("langs/ru.yml");
        this.messagesRUConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(inputStream));
        saveMessagesRUConfig();
        inputStream = this.getResource("langs/en.yml");
        this.messagesENConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(inputStream));
        saveMessagesENConfig();

        EmojiUtil.init(this);
        LangUtil.init(this);

        this.getCommand("schat").setExecutor(new SChat());
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new Chat(), this);
        pluginManager.registerEvents(new ChatDisplay(), this);
        pluginManager.registerEvents(new JoinQuitPlayer(), this);

        this.getLogger().info("SakuraChat is enabled!");
    }

    public static SakuraChat getPlugin() {
        return plugin;
    }

    public void saveEmojiConfig() {
        try {
            this.emojiConfig.save(String.valueOf(this.getDataFolder()) + "/emoji.yml");
        } catch (Exception var2) {
            Exception e = var2;
            this.getLogger().severe("Error saving emoji.yml: " + e.getMessage());
        }
    }

    public void saveMessagesRUConfig() {
        try {
            this.messagesRUConfig.save(String.valueOf(this.getDataFolder()) + "/langs/ru.yml");
        } catch (Exception var2) {
            Exception e = var2;
            this.getLogger().severe("Error saving ru.yml: " + e.getMessage());
        }
    }

    public void saveMessagesENConfig() {
        try {
            this.messagesENConfig.save(String.valueOf(this.getDataFolder()) + "/langs/en.yml");
        } catch (Exception var2) {
            Exception e = var2;
            this.getLogger().severe("Error saving en.yml: " + e.getMessage());
        }
    }

    public void onDisable() {
        this.getLogger().info("SakuraChat is disabled!");
    }
}
