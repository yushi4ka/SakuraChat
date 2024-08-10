package org.winterdev.SakuraChat.Listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.winterdev.SakuraChat.SakuraChat;
import org.winterdev.SakuraChat.Util.ColorUtil;
import org.winterdev.SakuraChat.Util.EmojiUtil;
import org.winterdev.SakuraChat.Util.LangUtil;

public class Chat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        FileConfiguration config = SakuraChat.getPlugin().getConfig();

        Player player = event.getPlayer();
        String message = event.getMessage();

        String tag = LangUtil.message("tag");

        boolean globalChatEnabled = config.getBoolean("global-chat", true);
        String globalChatSyntax = config.getString("global-chat-syntax", "!");

        if (message.startsWith(globalChatSyntax)) {
            if (!globalChatEnabled) {
                String chatdisabled = LangUtil.message("Messages.global-chat-disabled");
                String formatChatDisabled = ColorUtil.color(tag + chatdisabled);
                player.sendMessage(formatChatDisabled);
                event.setCancelled(true);
                return;
            }

            message = message.substring(globalChatSyntax.length());
            String format = LangUtil.message("Chat-Messages.global-chat-format");
            format = PlaceholderAPI.setPlaceholders(player, format);
            format = format.replace("%name%", player.getName());
            format = format.replace("%message%", message);

            String formatMessage = EmojiUtil.emoji(format);
            String formattedMessage = ColorUtil.color(formatMessage);

            event.setCancelled(true);
            Bukkit.broadcastMessage(formattedMessage);
        } else {
            String format = LangUtil.message("Chat-Messages.local-chat-format");
            format = PlaceholderAPI.setPlaceholders(player, format);
            format = format.replace("%name%", player.getName());
            format = format.replace("%message%", message);

            String formatMessage = EmojiUtil.emoji(format);
            String formattedMessage = ColorUtil.color(formatMessage);

            event.setCancelled(true);
            if (globalChatEnabled) {
                for (Player p : player.getWorld().getPlayers()) {
                    if (p.getLocation().distance(player.getLocation()) <= config.getInt("local-chat-radius")) {
                        p.sendMessage(ColorUtil.color(formattedMessage));
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
                    String noPlayersNearbyMessage = LangUtil.message("Messages.no-players-nearby");
                    player.sendMessage(ColorUtil.color(tag + noPlayersNearbyMessage));
                }
            } else {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(ColorUtil.color(formattedMessage));
                }
            }
        }
    }
}
