package org.winterdev.SakuraChat.Commands;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.winterdev.SakuraChat.SakuraChat;

import java.util.ArrayList;
import java.util.List;

public class SChat implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            return false;
        }

        Player player = (Player) commandSender;

        if (strings[0].equals("reload")) {
            FileConfiguration config = SakuraChat.getPlugin().getConfig();
            
            SakuraChat.getPlugin().reloadConfig();
            String reloadMessage = config.getString("Messages.cmd-reload-message");
            player.sendMessage(reloadMessage);
        } else if (strings[0].equals("test")) {
            String string = "Hello World!";
            string = PlaceholderAPI.setPlaceholders(player, string);
            player.sendMessage(string);
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> tab = new ArrayList<>();
        if (strings.length == 1) {
            tab.add("reload");
            tab.add("test");
        }
        return tab.isEmpty() ? new ArrayList<>() : tab;
    }
}