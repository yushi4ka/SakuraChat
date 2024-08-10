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
import org.winterdev.SakuraChat.Util.LangUtil;

public class SChat implements CommandExecutor, TabCompleter {
    public SChat() {
    }

    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) {
            return false;
        } else {
            FileConfiguration config = SakuraChat.getPlugin().getConfig();

            String tag = LangUtil.message("tag");
            if (strings[0].equals("reload")) {
                if (player.hasPermission("sakurachat.admin")) {
                    SakuraChat.getPlugin().reloadConfig();

                    String reloadMessage = LangUtil.message("Messages.cmd-reload-message");
                    player.sendMessage(ColorUtil.color(tag + reloadMessage));

                    EmojiUtil.reloadEmojis(SakuraChat.getPlugin());
                    LangUtil.reloadMessages(SakuraChat.getPlugin());
                } else {
                    String nopermissionMessage = LangUtil.message("Messages.cmd-no-permission");
                    player.sendMessage(ColorUtil.color(tag + nopermissionMessage));
                }
            } else if (strings[0].equals("lang")) {
                if (player.hasPermission("sakurachat.admin")) {
                    if (strings.length == 2) {
                        String lang = strings[1];
                        if (lang.equalsIgnoreCase("ru") || lang.equalsIgnoreCase("en")) {
                            config.set("lang", lang + ".yml");
                            SakuraChat.getPlugin().saveConfig();
                            LangUtil.init(SakuraChat.getPlugin());
                            String langChangedMessage = LangUtil.message("Messages.lang-changed");
                            player.sendMessage(ColorUtil.color(tag + langChangedMessage));
                        } else {
                            String invalidLanguageMessage = LangUtil.message("Messages.invalid-language");
                            player.sendMessage(ColorUtil.color(tag + invalidLanguageMessage));
                        }
                    } else {
                        String usageMessage = LangUtil.message("Messages.lang-usage");
                        player.sendMessage(ColorUtil.color(tag + usageMessage));
                    }
                } else {
                    String nopermissionMessage = LangUtil.message("Messages.cmd-no-permission");
                    player.sendMessage(ColorUtil.color(tag + nopermissionMessage));
                }
            }

            return false;
        }
    }

    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> tab = new ArrayList();
        if (strings.length == 1 && commandSender.hasPermission("sakurachat.admin")) {
            tab.add("reload");
            tab.add("lang");
        } else if (strings.length == 3 && commandSender.hasPermission("sakurachat.admin") && strings[2].equals("lang")) {
            tab.add("ru");
            tab.add("en");
        }
        return tab.isEmpty() ? new ArrayList() : tab;
    }
}
