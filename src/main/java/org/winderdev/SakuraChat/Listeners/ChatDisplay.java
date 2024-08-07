package org.winterdev.SakuraChat.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.winterdev.SakuraChat.SakuraChat;
import org.winterdev.SakuraChat.Util.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChatDisplay implements Listener {
    private static Map<UUID, ArmorStand> stands = new HashMap<>();

    @EventHandler
    public void onPlayerChat(final AsyncPlayerChatEvent event) {
        FileConfiguration config = SakuraChat.getPlugin().getConfig();
        if (config.getBoolean("chat-display.enable")) {
            final Player player = event.getPlayer();
            final String message = event.getMessage();

            if (message.startsWith(config.getString("global-chat-syntax", "!"))) {
                return;
            }

            String format = config.getString("chat-display.format");
            format = format.replace("%message%", message);
            String formatedMessage = EmojiUtil.emoji(format);

            UUID uuid = player.getUniqueId();
            if (stands.containsKey(uuid)) {
                ArmorStand stand = stands.get(uuid);
                stand.setCustomName(ColorUtil.color(formatedMessage));
            } else {
                Bukkit.getScheduler().runTask(SakuraChat.getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        ArmorStand stand = player.getWorld().spawn(player.getLocation().add(0, 0, 0), ArmorStand.class);

                        stand.setCustomName(ColorUtil.color(formatedMessage));
                        stand.setCustomNameVisible(true);
                        stand.setGravity(false);
                        stand.setVisible(false);

                        stands.put(uuid, stand);

                        int expirationTime = config.getInt("chat-display.expiration-time");
                        Bukkit.getScheduler().runTaskLater(SakuraChat.getPlugin(), () -> {
                            stand.remove();
                            stands.remove(uuid);
                        }, expirationTime * 20L);
                    }
                });
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (stands.containsKey(uuid)) {
            ArmorStand stand = stands.get(uuid);
            stand.teleport(player.getLocation().add(0, 0, 0));
        }
    }
}
