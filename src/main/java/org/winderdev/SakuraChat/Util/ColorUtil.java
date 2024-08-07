package org.winterdev.SakuraChat.Util;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtil {
    public ColorUtil() {
    }

    public static String color(String message) {
        Pattern pattern = Pattern.compile("&#([A-Fa-f0-9]{6})");

        String color;
        String replacement;
        for(Matcher matcher = pattern.matcher(message); matcher.find(); message = message.replace("&#" + color, replacement)) {
            color = matcher.group(1);
            replacement = ChatColor.of("#" + color).toString();
        }

        message = message.replace("{n}", "\n");
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
