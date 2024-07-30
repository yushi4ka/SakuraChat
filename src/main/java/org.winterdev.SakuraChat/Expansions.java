package org.winterdev.SakuraChat;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class Expansions extends PlaceholderExpansion {

    private final SakuraChat plugin;

    public Expansions(SakuraChat plugin) {
        this.plugin = plugin;
    }

    @Override
    @NotNull
    public String getAuthor() {
        return String.join(", ", plugin.getDescription().getAuthors());
    }

    @Override
    @NotNull
    public String getIdentifier() {
        return "SakuraChat";
    }

    @Override
    @NotNull
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {

        if (params.equalsIgnoreCase("exp")) {
            return "Exp: " + player.getPlayer().getExp();
        }

        return null; //
    }
}
