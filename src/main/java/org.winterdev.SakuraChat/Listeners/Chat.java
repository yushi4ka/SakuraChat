package org.winterdev.SakuraChat.Listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.winterdev.SakuraChat.SakuraChat;

public class Chat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        FileConfiguration config = SakuraChat.getPlugin().getConfig();

        Player player = event.getPlayer();
        String message = event.getMessage();

        boolean globalChatEnabled = config.getBoolean("global-chat", true);
        String globalChatSyntax = config.getString("global-chat-syntax", "");

        if (message.startsWith(globalChatSyntax)) {
            if (!globalChatEnabled) {
                String string = config.getString("Messages.global-chat-disabled");
                player.sendMessage(string);
                event.setCancelled(true);
                return;
            }

            message = message.substring(globalChatSyntax.length());
            String format = config.getString("Chat-Messages.global-chat-format");
            format = PlaceholderAPI.setPlaceholders(player, format);
            format = format.replace("%name%", player.getName());
            format = format.replace("%message%", message);

            String formattedMessage = ChatColor.translateAlternateColorCodes('&', format);

            event.setCancelled(true);
            Bukkit.broadcastMessage(formattedMessage);
        } else {
            String format = config.getString("Chat-Messages.local-chat-format");
            format = PlaceholderAPI.setPlaceholders(player, format);
            format = format.replace("%name%", player.getName());
            format = format.replace("%message%", message);

            String formattedMessage = ChatColor.translateAlternateColorCodes('&', format);

            event.setCancelled(true);
            if (globalChatEnabled) {
                for (Player p : player.getWorld().getPlayers()) {
                    if (p.getLocation().distance(player.getLocation()) <= config.getInt("local-chat-radius")) {
                        p.sendMessage(formattedMessage);
                    }
                }
                boolean playersNearby = false;
                for (Player p : player.getWorld().getPlayers()) {
                    if (p != player && p.getLocation().distance(player.getLocation()) <= config.getInt("local-chat-radius")) {
                        playersNearby = true;
                        break;
                    }
                }
                if (!playersNearby) {
                    String noPlayersNearbyMessage = config.getString("Messages.no-players-nearby");
                    player.sendMessage(noPlayersNearbyMessage);
                }
            } else {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(formattedMessage);
                }
            }
        }
    }
}