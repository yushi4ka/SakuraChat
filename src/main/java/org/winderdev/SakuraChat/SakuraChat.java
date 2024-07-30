package org.winterdev.SakuraChat;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.winterdev.SakuraChat.Commands.SChat;
import org.winterdev.SakuraChat.Listeners.JoinQuitPlayer;
import org.winterdev.SakuraChat.Listeners.Chat;

public final class SakuraChat extends JavaPlugin {

    private static SakuraChat plugin;

    @Override
    public void onEnable() {
        plugin = this;

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        getCommand("schat").setExecutor(new SChat());

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new JoinQuitPlayer(), this);
        pluginManager.registerEvents(new Chat(), this);

        new Expansions(this).register();

        getLogger().info("SakuraChat is enabled!");
    }

    public static SakuraChat getPlugin() {
        return plugin;
    }

    @Override
    public void onDisable() {
        getLogger().info("SakuraChat is disabled!");
    }
}
