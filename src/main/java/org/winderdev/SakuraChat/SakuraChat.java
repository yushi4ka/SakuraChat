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

public final class SakuraChat extends JavaPlugin {
    private static SakuraChat plugin;
    private YamlConfiguration emojiConfig;

    public SakuraChat() {
    }

    public void onEnable() {
        plugin = this;

        this.getConfig().options().copyDefaults();
        this.saveDefaultConfig();

        InputStream inputStream = this.getResource("emoji.yml");
        this.emojiConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(inputStream));

        EmojiUtil.init(this);

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

    public void onDisable() {
        try {
            this.saveDefaultConfig();
            this.emojiConfig.save(String.valueOf(this.getDataFolder()) + "/emoji.yml");
        } catch (Exception var2) {
            Exception e = var2;
            this.getLogger().severe("Error saving config: " + e.getMessage());
        }

        this.getLogger().info("SakuraChat is disabled!");
    }
}
