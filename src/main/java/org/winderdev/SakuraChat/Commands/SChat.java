package org.winterdev.SakuraChat.Commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.winterdev.SakuraChat.SakuraChat;
import org.winterdev.SakuraChat.Util.ColorUtil;
import org.winterdev.SakuraChat.Util.EmojiUtil;

public class SChat implements CommandExecutor, TabCompleter {
    public SChat() {
    }

    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) {
            return false;
        } else {
            FileConfiguration config = SakuraChat.getPlugin().getConfig();
            if (strings[0].equals("reload")) {
                String tag = config.getString("Messages.tag");
                if (player.hasPermission("schat.admin")) {
                    SakuraChat.getPlugin().reloadConfig();
                    config = SakuraChat.getPlugin().getConfig();

                    String reloadMessage = config.getString("Messages.cmd-reload-message");
                    player.sendMessage(ColorUtil.color(tag + reloadMessage));

                    EmojiUtil.reloadEmojis(SakuraChat.getPlugin());
                } else {
                    String nopermissionMessage = config.getString("Messages.cmd-no-permission");
                    player.sendMessage(ColorUtil.color(tag + nopermissionMessage));
                }
            } else if (strings[0].equals("test")) {
                String tag = config.getString("Messages.tag");
                if (player.hasPermission("schat.admin")) {
                    String testMessage = "§aHello World!";
                    player.sendMessage(ColorUtil.color(tag + testMessage));
                } else {
                    String nopermissionMessage = config.getString("Messages.cmd-no-permission");
                    player.sendMessage(ColorUtil.color(tag + nopermissionMessage));
                }
            }

            return false;
        }
    }

    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> tab = new ArrayList();
        if (strings.length == 1 && commandSender.hasPermission("schat.admin")) {
            tab.add("reload");
            tab.add("test");
        }

        return tab.isEmpty() ? new ArrayList() : tab;
    }
}
