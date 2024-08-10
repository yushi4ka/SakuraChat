package org.winterdev.SakuraChat.Listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.winterdev.SakuraChat.SakuraChat;
import org.winterdev.SakuraChat.Util.LangUtil;

public class JoinQuitPlayer implements Listener {

    @EventHandler
    private void JoinServer(PlayerJoinEvent e) {
        FileConfiguration config = SakuraChat.getPlugin().getConfig();

        Player player = e.getPlayer();
        String joinMessage = LangUtil.message("JoinQuit-Messages.Join-message");
        if (joinMessage != null) {
            joinMessage = PlaceholderAPI.setPlaceholders(player, joinMessage);
            joinMessage = joinMessage.replace("%name%", player.getName());
            String formattedMessage = ChatColor.translateAlternateColorCodes('&', joinMessage);
            e.setJoinMessage(formattedMessage);
        }
    }

    @EventHandler
    private void QuitServer(PlayerQuitEvent e) {
        FileConfiguration config = SakuraChat.getPlugin().getConfig();

        Player player = e.getPlayer();
        String quitMessage = LangUtil.message("JoinQuit-Messages.Quit-message");
        if (quitMessage != null) {
            quitMessage = PlaceholderAPI.setPlaceholders(player, quitMessage);
            quitMessage = quitMessage.replace("%name%", player.getName());
            String formattedMessage = ChatColor.translateAlternateColorCodes('&', quitMessage);
            e.setQuitMessage(formattedMessage);
        }
    }
}
